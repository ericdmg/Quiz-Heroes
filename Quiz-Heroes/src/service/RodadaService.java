//package service;
//
//import model.Jogador;
//import model.Pergunta;
//import model.Quiz;
//import view.QuizCLI;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RodadaService {
//
//    private final QuizCLI view;
//    private final OllamaClient ollamaClient;
//    private final PerguntaService perguntaService;
//
//    public RodadaService(QuizCLI view, OllamaClient ollamaClient, PerguntaService perguntaService) {
//        this.view = view;
//        this.ollamaClient = ollamaClient;
//        this.perguntaService = perguntaService;
//    }
//
//    public void executarRodadas(Quiz quiz) {
//        for (Jogador jogador : quiz.getJogadores()) {
//            view.mostrarMensagem("\nRodada para: " + jogador.getNome());
//
//            // gerar perguntas diferentes para este jogador
//            List<Pergunta> perguntasRespondidas = new ArrayList<>();
//            List<Pergunta> perguntasIndividuais = perguntaService.gerarPerguntas(quiz.getTema());
//
//            for (Pergunta pergunta : perguntasIndividuais) {
//                view.mostrarMensagem("\nPergunta: " + pergunta.getEnunciado());
//                String resposta = view.capturarRespostaTexto();
//                pergunta.setRespostaDoJogador(resposta);
//
//                view.mostrarMensagem("🔎 Avaliando resposta...");
//
//                String contexto = this.ollamaClient.construirContextoHistorico(perguntasRespondidas);
//
//                StringBuilder explicacao = new StringBuilder();
//                boolean correta = this.ollamaClient.avaliarCertoErradoComContexto(contexto, pergunta, explicacao);
//
//                if (correta) {
//                    jogador.adicionarPontos(pergunta.getPontuacao());
//                    view.mostrarMensagem("✅ Resposta correta! +" + pergunta.getPontuacao() + " pontos.");
//                } else {
//                    view.mostrarMensagem("❌ Resposta incorreta.");
//                    if (!explicacao.isEmpty()) {
//                        view.mostrarMensagem("💡 Explicação: " + explicacao.toString());
//                    }
//                }
//                jogador.registrarResposta(pergunta);
//
//                perguntasRespondidas.add(pergunta);
//            }
//        }
//    }
//}
