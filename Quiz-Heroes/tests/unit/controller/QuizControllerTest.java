package unit.controller;

import controller.QuizController;
import model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unit.service.OllamaClientStub;
import unit.view.QuizCLIStub;
import view.QuizCLI;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuizControllerTest {

    private QuizController controller;
    private QuizCLIStub viewStub;
    private OllamaClientStub ollamaStub;

    @BeforeEach
    void setUp() throws Exception {
        this.viewStub = new QuizCLIStub();
        this.controller = new QuizControllerComInjecao(this.viewStub, new OllamaClientStub());
        this.ollamaStub = new OllamaClientStub();
    }

    @Test
    void iniciarNovoQuiz_deveRegistrarQuizComJogadoresETema() throws Exception {
        this.controller.iniciarNovoQuiz();

        Set<Quiz> quizzes = acessarQuizzesPrivado(this.controller);
        assertEquals(1, quizzes.size());

        Quiz quiz = quizzes.iterator().next();
        assertEquals(2, quiz.getJogadores().size());
        assertNotNull(quiz.getTema());

        assertTrue(this.viewStub.contemMensagem("Bem-vindo ao Quiz de Perguntas e Respostas!"));
    }

    @Test
    void exibirJogadores_deveExibirNomesDosJogadores() throws Exception {
        this.controller.iniciarNovoQuiz();
        this.viewStub.limparMensagens();

        this.controller.exibirJogadores();

        assertTrue(this.viewStub.contemMensagem("Jogador1"));
        assertTrue(this.viewStub.contemMensagem("Jogador2"));
    }

    @Test
    void listarRelatorios_deveExibirRelatorioDosQuizzes() throws Exception {
        this.controller.iniciarNovoQuiz();
        this.viewStub.limparMensagens();

        this.controller.listarRelatorios();

        assertTrue(this.viewStub.contemMensagem("📊 Relatórios de quizzes anteriores"));
        assertTrue(this.viewStub.contemMensagem("Tema:"));
        assertTrue(this.viewStub.contemMensagem("Jogador1"));
        assertTrue(this.viewStub.contemMensagem("Pontuação total"));
    }

    // Auxiliar para acessar o campo privado "quizzes"
    private Set<Quiz> acessarQuizzesPrivado(QuizController controller) throws Exception {
        Field field = QuizController.class.getDeclaredField("quizzes");
        field.setAccessible(true);
        return (Set<Quiz>) field.get(controller);
    }

    // Subclasse auxiliar do controller com injeção de dependências
    static class QuizControllerComInjecao extends QuizController {
        public QuizControllerComInjecao(QuizCLI view, OllamaClientStub ollamaStub) {
            super(view, ollamaStub);
        }
    }
}
