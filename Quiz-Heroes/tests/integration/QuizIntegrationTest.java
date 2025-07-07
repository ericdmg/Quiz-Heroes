package integration;

import model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.QuizService;
import unit.service.OllamaClientStub;
import unit.view.QuizCLIStub;

import static org.junit.jupiter.api.Assertions.*;

public class QuizIntegrationTest {

    private QuizService quizService;
    private Quiz quiz;

    @BeforeEach
    void setup() {
        quizService = new QuizService(new QuizCLIStub(), new OllamaClientStub());
        quiz = new Quiz();
    }

    @Test
    void rodadaCompleta_cadastra_joga_geraRelatorio() {
        // Cadastro
        quizService.cadastrarJogadores(quiz);
        quiz.setTema("História");

        assertEquals(2, quiz.getJogadores().size());

        // Execução das rodadas (simuladas com respostas certas via stub)
        quizService.executarRodadas(quiz);

        // Verificação das pontuações
        quiz.getJogadores().values().forEach(jogador -> {
            assertEquals(5, jogador.getPerguntasRespondidas().size());
            assertTrue(jogador.getPontuacao() > 0);
        });

        // Geração do relatório (apenas executa sem exception)
        quizService.gerarRelatorioFinal(quiz);
    }
}
