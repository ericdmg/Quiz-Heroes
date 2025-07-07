package unit.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.QuizCLI;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class QuizCLITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void mostrarMensagem_imprimeNoConsole() {
        QuizCLI cli = new QuizCLI();
        cli.mostrarMensagem("Olá, mundo!");
        assertTrue(outContent.toString().contains("Olá, mundo!"));
    }

    @Test
    void perguntarNumeroJogadores_lêEntradaSimulada() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        QuizCLI cli = new QuizCLI();
        int resultado = cli.perguntarNumeroJogadores(1, 5);
        assertEquals(3, resultado);
    }

    @Test
    void perguntarNomeJogador_lêNomeSimulado() {
        String input = "Alice\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        QuizCLI cli = new QuizCLI();
        String nome = cli.perguntarNomeJogador(1);
        assertEquals("Alice", nome);
    }

    @Test
    void capturarRespostaTexto_lêRespostaSimulada() {
        String input = "Errado\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        QuizCLI cli = new QuizCLI();
        String resposta = cli.capturarRespostaTexto();
        assertEquals("Errado", resposta);
    }
}
