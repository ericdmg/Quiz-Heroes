package unit.service;

import model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.QuizService;
import unit.view.QuizCLIStub;

import static org.junit.jupiter.api.Assertions.*;

class QuizServiceTest {

    private QuizService service;

    @BeforeEach
    void setUp() {
        service = new QuizService(new QuizCLIStub(), new OllamaClientStub());
    }

    @Test
    void testCadastrarJogadores() {
        Quiz quiz = new Quiz();
        service.cadastrarJogadores(quiz);
        assertEquals(2, quiz.getJogadores().size());
    }

    @Test
    void testGerarRelatorioFinal() {
        Quiz quiz = new Quiz();
        service.cadastrarJogadores(quiz);
        quiz.setTema("História");

        service.executarRodadas(quiz);

        // Vamos apenas verificar se não lança exceção e cobre o método
        assertDoesNotThrow(() -> service.gerarRelatorioFinal(quiz));
    }

    @Test
    void testExecutarRodadas() {
        Quiz quiz = new Quiz();
        service.cadastrarJogadores(quiz);
        quiz.setTema("Ciência");

        service.executarRodadas(quiz);

        var jogador = quiz.getJogadores().values().iterator().next();
        assertTrue(jogador.getPontuacao() > 0);
        assertEquals(5, jogador.getPerguntasRespondidas().size());
    }
}
