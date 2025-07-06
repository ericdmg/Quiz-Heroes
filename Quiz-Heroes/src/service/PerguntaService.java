//package service;
//
//import model.Pergunta;
//import model.Pergunta.Dificuldade;
//import view.QuizCLI;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PerguntaService {
//    private QuizCLI view;
//    private OllamaClient ollamaClient;
//
//    public PerguntaService(QuizCLI quizCLI, OllamaClient ollamaClient) {
//        this.view = quizCLI;
//        this.ollamaClient = ollamaClient;
//    }
//
//    public List<Pergunta> gerarPerguntas(String tema) {
//        List<Pergunta> perguntas = new ArrayList<>();
//        this.view.mostrarMensagem("🧠 Gerando pergunta...");
//        // 3 fáceis
//        for (int i = 0; i < 3; i++) {
//            perguntas.add(novaPergunta(tema, Dificuldade.FACIL, 10));
//        }
//
//        // 1 média
//        for (int i = 0; i < 1; i++) {
//            perguntas.add(novaPergunta(tema, Dificuldade.MEDIA, 20));
//        }
//
//        // 1 difícil
//        perguntas.add(novaPergunta(tema, Dificuldade.DIFICIL, 30));
//
//        return perguntas;
//    }
//
//    private Pergunta novaPergunta(String tema, Dificuldade dificuldade, int pontos) {
//        String prompt = String.format(
//                "Crie uma afirmação %s sobre o tema \"%s\". " +
//                        "A frase deve terminar com 'Certo ou Errado?'. Seja original e evite repetir frases anteriores. " +
//                        "Apenas retorne a frase. [VARIACAO: %s]",
//                dificuldade.name().toLowerCase(), tema, java.util.UUID.randomUUID().toString().substring(0, 8)
//        );
//
//        String resposta = ollamaClient.enviarPrompt(prompt);
//        String enunciado = resposta != null ? resposta : "[Erro ao gerar]";
//
//        Pergunta pergunta = new Pergunta(enunciado, -1, dificuldade, pontos);
//        pergunta.setPromptGerado(prompt);
//        pergunta.setRespostaGerada(resposta);
//
//        return pergunta;
//
//    }
//}
