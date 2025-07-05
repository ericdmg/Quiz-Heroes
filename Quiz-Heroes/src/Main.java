import controller.QuizController;
import service.OllamaClient;

public class Main {
    public static void main(String[] args){
//        String prompt = "Compare as respostas abaixo e diga quão similares elas são em conteúdo e sentido.\n\n" +
//                "Pergunta: \"Qual a capital da França?\"\n" +
//                "Resposta do jogador: \"Paris\"\n\n" +
//                "Responda com: {\"similaridade\": 100} e nada mais.";
//        String resposta = OllamaClient.enviarPrompt(prompt);
//        System.out.println("[DEBUG resposta]: " + resposta);


        QuizController controller = new QuizController();
        controller.iniciarQuiz();
    }
}