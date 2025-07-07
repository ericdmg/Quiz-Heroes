package unit.model;

import model.Jogador;
import model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
    }

    @Test
    void setTemaAndGetTema() {
        quiz.setTema("História");
        assertEquals("História", quiz.getTema());
    }

    @Test
    void getJogadoresInitiallyEmpty() {
        assertNotNull(quiz.getJogadores());
        assertTrue(quiz.getJogadores().isEmpty(), "A lista de jogadores deve estar vazia inicialmente.");
    }

    @Test
    void setAndGetJogadores() {
        Map<String, Jogador> jogadores = new HashMap<>();
        Jogador j1 = new Jogador("Alice");
        Jogador j2 = new Jogador("Bob");

        jogadores.put(j1.getId(), j1);
        jogadores.put(j2.getId(), j2);

        quiz.setJogadores(jogadores);

        Map<String, Jogador> resultado = quiz.getJogadores();
        assertEquals(2, resultado.size());
        assertTrue(resultado.containsValue(j1));
        assertTrue(resultado.containsValue(j2));
    }
}
