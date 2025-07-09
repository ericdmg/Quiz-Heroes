package service;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import util.OllamaParser;
import view.QuizCLI;

import java.util.*;

public class QuizService {
    private static final List<String> TEMAS = Arrays.asList(
            "História",
            "Ciência",
            "Geografia",
            "Matemática",
            "Literatura",
            "Tecnologia",
            "Esportes"
    );
    private final QuizCLI view;
    private final OllamaClient ollamaClient;

    private static final Random random = new Random();

    public QuizService(QuizCLI view, OllamaClient ollamaClient) {
        this.view = view;
        this.ollamaClient = ollamaClient;
    }

    public String sortearTema() {
        int index = random.nextInt(TEMAS.size());
        return TEMAS.get(index);
    }


    public List<Pergunta> gerarPerguntas(String tema) {
        List<Pergunta> perguntas = new ArrayList<>();
        this.view.mostrarMensagem("🧠 Gerando pergunta...");
        // 3 fáceis
        for (int i = 0; i < 3; i++) {
            perguntas.add(novaPergunta(tema, Pergunta.Dificuldade.FACIL, 10));
        }

        // 1 média
        for (int i = 0; i < 1; i++) {
            perguntas.add(novaPergunta(tema, Pergunta.Dificuldade.MEDIA, 20));
        }

        // 1 difícil
        perguntas.add(novaPergunta(tema, Pergunta.Dificuldade.DIFICIL, 30));

        return perguntas;
    }

    private Pergunta novaPergunta(String tema, Pergunta.Dificuldade dificuldade, int pontos) {
        String prompt = String.format(
                "Crie uma afirmação %s sobre o tema \"%s\". " +
                        "A frase deve terminar com 'Certo ou Errado?'. Seja original e evite repetir frases anteriores. " +
                        "Apenas retorne a frase. [VARIACAO: %s]",
                dificuldade.name().toLowerCase(), tema, java.util.UUID.randomUUID().toString().substring(0, 8)
        );

        String resposta = ollamaClient.enviarPrompt(prompt);
        String enunciado = resposta != null ? resposta : "[Erro ao gerar]";

        Pergunta pergunta = new Pergunta(enunciado, -1, dificuldade, pontos);
        pergunta.setPromptGerado(prompt);
        pergunta.setRespostaGerada(resposta);

        return pergunta;

    }

    public void executarRodadas(Quiz quiz) {
        for (Jogador jogador : quiz.getJogadores().values()) {
            view.mostrarMensagem("\nRodada para: " + jogador.getNome());

            // gerar perguntas diferentes para este jogador
            List<Pergunta> perguntasRespondidas = new ArrayList<>();
            List<Pergunta> perguntasIndividuais = this.gerarPerguntas(quiz.getTema());

            for (Pergunta pergunta : perguntasIndividuais) {
                view.mostrarMensagem("\nPergunta: " + pergunta.getEnunciado());
                String resposta = view.capturarRespostaTexto();
                pergunta.setRespostaDoJogador(resposta);

                view.mostrarMensagem("🔎 Avaliando resposta...");

                String contexto = this.construirContextoHistorico(perguntasRespondidas);

                StringBuilder explicacao = new StringBuilder();
                boolean correta = this.avaliarCertoErradoComContexto(contexto, pergunta, explicacao);

                if (correta) {
                    jogador.adicionarPontos(pergunta.getPontuacao());
                    pergunta.setPontosRecebidos(pergunta.getPontuacao());  // <-- CORRIGIDO
                    view.mostrarMensagem("✅ Resposta correta! +" + pergunta.getPontuacao() + " pontos.");
                } else {
                    pergunta.setPontosRecebidos(0);  // <-- também importante
                    view.mostrarMensagem("❌ Resposta incorreta.");
                    if (!explicacao.isEmpty()) {
                        view.mostrarMensagem("💡 Explicação: " + explicacao.toString());
                    }
                }
                jogador.registrarResposta(pergunta);

                perguntasRespondidas.add(pergunta);
            }
        }
    }

    public void gerarRelatorioFinal(Quiz quiz) {
        this.view.mostrarMensagem("\n📊 RELATÓRIO FINAL DO QUIZ\n");

        List<Jogador> jogadoresOrdenados = new ArrayList<>(quiz.getJogadores().values());
        jogadoresOrdenados.sort((a, b) -> Integer.compare(b.getPontuacao(), a.getPontuacao()));

        for (int i = 0; i < jogadoresOrdenados.size(); i++) {
            Jogador jogador = jogadoresOrdenados.get(i);

            String medalha = switch (i) {
                case 0 -> "🥇";
                case 1 -> "🥈";
                case 2 -> "🥉";
                default -> "   ";
            };

            view.mostrarMensagem(medalha + " Jogador: " + jogador.getNome() + " (ID: " + jogador.getId() + ")");
            view.mostrarMensagem("Pontuação total: " + jogador.getPontuacao());

            int total = jogador.getPerguntasRespondidas().size();
            long acertos = jogador.getPerguntasRespondidas().stream()
                    .filter(p -> p.getPontosRecebidos() > 0)
                    .count();

            int percentual = total > 0 ? (int) ((100.0 * acertos) / total) : 0;
            view.mostrarMensagem("Acertos: " + acertos + "/" + total + " (" + percentual + "%)");

            for (Pergunta p : jogador.getPerguntasRespondidas()) {
                String status = p.getPontosRecebidos() > 0 ? "✅" : "❌";
                view.mostrarMensagem(status + " " + p.getEnunciado());
                view.mostrarMensagem("Resposta: " + p.getRespostaDoJogador());
                view.mostrarMensagem("Pontos: " + p.getPontosRecebidos());

                String erro = p.getExplicacaoErro();
                if (erro != null && !erro.isBlank()) {
                    view.mostrarMensagem("💡 Explicação: " + erro);
                }
                view.mostrarMensagem("");
            }

            view.mostrarMensagem("--------------------------------------------------");
        }
    }

    public void cadastrarJogadores(Quiz quiz) {
        Map<String, Jogador> jogadores = new HashMap<>();
        int total = view.perguntarNumeroJogadores(1, 5);

        for (int i = 0; i < total; i++) {
            String nome = view.perguntarNomeJogador(i + 1);
            Jogador jogador = new Jogador(nome);
            jogadores.put(jogador.getId(), jogador);
            view.mostrarMensagem("✅ Jogador '" + nome + "' registrado com ID: " + jogador.getId());
        }

        quiz.setJogadores(jogadores);
    }

    public String construirContextoHistorico(List<Pergunta> perguntasAnteriores) {
        StringBuilder contexto = new StringBuilder();
        for (Pergunta p : perguntasAnteriores) {
            if (p.getRespostaDoJogador() != null) {
                contexto.append("Pergunta: ").append(p.getEnunciado()).append("\n");
                contexto.append("Resposta do jogador: ").append(p.getRespostaDoJogador()).append("\n\n");
            }
        }
        return contexto.toString();
    }

    public boolean avaliarCertoErradoComContexto(String contexto, Pergunta pergunta, StringBuilder explicacaoOut) {
        // Montar o JSON com a pergunta e a resposta do jogador
        String jsonPergunta = String.format(
                "{\n" +
                        "  \"pergunta\": \"%s\",\n" +
                        "  \"resposta_usuario\": \"%s\"\n" +
                        "}",
                pergunta.getEnunciado(),
                pergunta.getRespostaDoJogador()
        );

        // Prompt direto, sem contexto acumulado
        String prompt = String.format(
                "A seguir, avalie APENAS a pergunta e a resposta fornecida abaixo:\n\n" +
                        "%s\n\n" +
                        "Se a resposta do jogador estiver correta, retorne:\n" +
                        "{\"correta\": true}\n" +
                        "Se estiver incorreta, retorne:\n" +
                        "{\"correta\": false, \"explicacao\": \"Explique brevemente o erro.\"}\n\n" +
                        "Responda apenas com o JSON.",
                jsonPergunta
        );

        String resposta = this.ollamaClient.enviarPrompt(prompt);
        pergunta.setPromptGerado(prompt); // salvar o prompt final
        pergunta.setRespostaGerada(resposta); // salvar a resposta crua do modelo

        if (resposta == null) return false;

        boolean correta = OllamaParser.avaliarRespostaComExplicacao(resposta, explicacaoOut);
        return correta;
    }
}
