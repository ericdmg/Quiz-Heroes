package unit.service;

import model.Pergunta;
import model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.QuizService;
import unit.view.QuizCLIStub;

import java.util.ArrayList;
import java.util.List;

import static model.Pergunta.Dificuldade.FACIL;
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

        assertDoesNotThrow(() -> service.gerarRelatorioFinal(quiz));
    }

    @Test
    void testExecutarRodadas() {
        Quiz quiz = new Quiz();
        this.service.cadastrarJogadores(quiz);
        quiz.setTema("Ciência");

        this.service.executarRodadas(quiz);

        var jogador = quiz.getJogadores().values().iterator().next();
        assertTrue(jogador.getPontuacao() > 0);
        assertEquals(5, jogador.getPerguntasRespondidas().size());
    }

    @Test
    void construirContextoHistorico_deveRetornarTextoFormatado() {
        List<Pergunta> perguntas = new ArrayList<>();

        Pergunta p1 = new Pergunta("O céu é azul?", 0, FACIL, 10);
        p1.setRespostaDoJogador("Certo");

        Pergunta p2 = new Pergunta("A água ferve a 50°C?", 0, FACIL, 10);
        p2.setRespostaDoJogador("Errado");

        perguntas.add(p1);
        perguntas.add(p2);

        String contexto = this.service.construirContextoHistorico(perguntas);

        assertTrue(contexto.contains("Pergunta: O céu é azul?"));
        assertTrue(contexto.contains("Resposta do jogador: Certo"));
        assertTrue(contexto.contains("Pergunta: A água ferve a 50°C?"));
        assertTrue(contexto.contains("Resposta do jogador: Errado"));
    }
}
