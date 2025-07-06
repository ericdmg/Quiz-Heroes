import controller.QuizController;
import service.OllamaClient;

public class Main {
    public static void main(String[] args){
        QuizController controller = new QuizController();
        controller.iniciarQuiz();
    }
}