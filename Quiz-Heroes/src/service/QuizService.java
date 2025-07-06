package service;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import view.QuizCLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        for (Jogador jogador : quiz.getJogadores()) {
            view.mostrarMensagem("\nRodada para: " + jogador.getNome());

            // gerar perguntas diferentes para este jogador
            List<Pergunta> perguntasRespondidas = new ArrayList<>();
            List<Pergunta> perguntasIndividuais = this.gerarPerguntas(quiz.getTema());

            for (Pergunta pergunta : perguntasIndividuais) {
                view.mostrarMensagem("\nPergunta: " + pergunta.getEnunciado());
                String resposta = view.capturarRespostaTexto();
                pergunta.setRespostaDoJogador(resposta);

                view.mostrarMensagem("🔎 Avaliando resposta...");

                String contexto = this.ollamaClient.construirContextoHistorico(perguntasRespondidas);

                StringBuilder explicacao = new StringBuilder();
                boolean correta = this.ollamaClient.avaliarCertoErradoComContexto(contexto, pergunta, explicacao);

                if (correta) {
                    jogador.adicionarPontos(pergunta.getPontuacao());
                    view.mostrarMensagem("✅ Resposta correta! +" + pergunta.getPontuacao() + " pontos.");
                } else {
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

    public void gerarRelatorioFinal(Quiz quiz, QuizCLI view) {
        view.mostrarMensagem("\n📊 RELATÓRIO FINAL DO QUIZ\n");

        // Ordena jogadores por pontuação (descendente)
        List<Jogador> jogadoresOrdenados = new ArrayList<>(quiz.getJogadores());
        jogadoresOrdenados.sort((a, b) -> Integer.compare(b.getPontuacao(), a.getPontuacao()));

        for (int i = 0; i < jogadoresOrdenados.size(); i++) {
            Jogador jogador = jogadoresOrdenados.get(i);

            // Medalha
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
                    .filter(p -> p.getPontuacao() > 0)
                    .count();

            int percentual = total > 0 ? (int) ((100.0 * acertos) / total) : 0;
            view.mostrarMensagem("Acertos: " + acertos + "/" + total + " (" + percentual + "%)");

            for (Pergunta p : jogador.getPerguntasRespondidas()) {
                String status = p.getPontuacao() > 0 ? "✅" : "❌";
                view.mostrarMensagem(status + " " + p.getEnunciado());
                view.mostrarMensagem("Resposta: " + p.getRespostaDoJogador());
                view.mostrarMensagem("Pontos: " + p.getPontuacao());

                String erro = p.getExplicacaoErro();
                if (erro != null && !erro.isBlank()) {
                    view.mostrarMensagem("💡 Explicação: " + erro);
                }
                view.mostrarMensagem(""); // linha em branco
            }

            view.mostrarMensagem("--------------------------------------------------");
        }
    }


    public void cadastrarJogadores(Quiz quiz) {
        List<Jogador> jogadores = new ArrayList<>();
        int total = this.view.perguntarNumeroJogadores(1, 5);
        for (int i = 0; i < total; i++) {
            String nome = this.view.perguntarNomeJogador(i + 1);
            String id = this.view.perguntarIdJogador(nome);
            jogadores.add(new Jogador(nome, id));
        }
        quiz.setJogadores(jogadores); // atualiza o objeto Quiz diretamente
    }
}
