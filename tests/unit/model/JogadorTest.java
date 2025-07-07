package unit.model;

import model.Jogador;
import model.Pergunta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JogadorTest {

    private Jogador jogador;

    @BeforeEach
    void setUp() {
        jogador = new Jogador("Eric");
    }

    @Test
    void testEquals() {
        Jogador jogador2 = new Jogador("Eric");
        // Força mesmo ID e pontuação para igualdade
        jogador2.adicionarPontos(jogador.getPontuacao());
        setPrivateId(jogador2, jogador.getId());

        assertEquals(jogador, jogador2);
    }

    @Test
    void testHashCode() {
        Jogador jogador2 = new Jogador("Eric");
        jogador2.adicionarPontos(jogador.getPontuacao());
        setPrivateId(jogador2, jogador.getId());

        assertEquals(jogador.hashCode(), jogador2.hashCode());
    }

    @Test
    void getNome() {
        assertEquals("Eric", jogador.getNome());
    }

    @Test
    void getId() {
        assertNotNull(jogador.getId());
        assertEquals(8, jogador.getId().length());
    }

    @Test
    void getPontuacao() {
        assertEquals(0, jogador.getPontuacao());
    }

    @Test
    void adicionarPontos() {
        jogador.adicionarPontos(10);
        assertEquals(10, jogador.getPontuacao());

        jogador.adicionarPontos(5);
        assertEquals(15, jogador.getPontuacao());
    }

    @Test
    void registrarResposta() {
        Pergunta pergunta = new Pergunta(
                "Qual a capital do Brasil?",
                30,
                Pergunta.Dificuldade.FACIL,
                10
        );

        jogador.registrarResposta(pergunta);

        List<Pergunta> respondidas = jogador.getPerguntasRespondidas();
        assertEquals(1, respondidas.size());
        assertEquals(pergunta, respondidas.get(0));
    }


    @Test
    void getPerguntasRespondidas() {
        assertNotNull(jogador.getPerguntasRespondidas());
        assertTrue(jogador.getPerguntasRespondidas().isEmpty());
    }

    // Método auxiliar para setar ID manualmente via reflexão (não ideal, mas necessário para igualdade)
    private void setPrivateId(Jogador jogador, String idDesejado) {
        try {
            var field = Jogador.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(jogador, idDesejado);
        } catch (Exception e) {
            fail("Erro ao setar ID com reflexão: " + e.getMessage());
        }
    }
}
