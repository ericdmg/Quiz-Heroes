package unit.model;

import model.Pergunta;
import model.Pergunta.Dificuldade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PerguntaTest {

    @Test
    public void testEstaCorreta_CorrectAnswerSelected() {
        Pergunta pergunta = new Pergunta("Qual é a capital da França?", 2, Dificuldade.MEDIA, 10);
        assertTrue(pergunta.estaCorreta(2));
    }

    @Test
    public void testEstaCorreta_IncorrectAnswerSelected() {
        Pergunta pergunta = new Pergunta("Qual é a capital da França?", 2, Dificuldade.MEDIA, 10);
        assertFalse(pergunta.estaCorreta(1));
    }

    @Test
    public void testEstaCorreta_NegativeIndex() {
        Pergunta pergunta = new Pergunta("Qual é a capital da França?", 2, Dificuldade.MEDIA, 10);
        assertFalse(pergunta.estaCorreta(-1));
    }

    @Test
    public void testEstaCorreta_IndexGreaterThanPossible() {
        Pergunta pergunta = new Pergunta("Qual é a capital da França?", 2, Dificuldade.MEDIA, 10);
        assertFalse(pergunta.estaCorreta(5));
    }

    @Test
    public void testEstaCorreta_ZeroIndexWhenCorrectIsNonZero() {
        Pergunta pergunta = new Pergunta("Qual é a capital da França?", 2, Dificuldade.MEDIA, 10);
        assertFalse(pergunta.estaCorreta(0));
    }

    @Test
    public void testGettersBasicos() {
        Pergunta pergunta = new Pergunta("Quem descobriu o Brasil?", 0, Dificuldade.FACIL, 5);
        assertEquals("Quem descobriu o Brasil?", pergunta.getEnunciado());
        assertEquals(0, pergunta.getIndiceCorreto());
        assertEquals(Dificuldade.FACIL, pergunta.getDificuldade());
        assertEquals(5, pergunta.getPontuacao());
    }

    @Test
    public void testRespostaDoJogador() {
        Pergunta pergunta = new Pergunta("Pergunta teste", 1, Dificuldade.DIFICIL, 15);
        pergunta.setRespostaDoJogador("Letra C");
        assertEquals("Letra C", pergunta.getRespostaDoJogador());
    }

    @Test
    public void testRespostaGerada() {
        Pergunta pergunta = new Pergunta("Pergunta teste", 1, Dificuldade.DIFICIL, 15);
        pergunta.setRespostaGerada("Resposta correta é C.");
        assertEquals("Resposta correta é C.", pergunta.getRespostaGerada());
    }

    @Test
    public void testPromptGerado() {
        Pergunta pergunta = new Pergunta("Pergunta teste", 1, Dificuldade.MEDIA, 10);
        pergunta.setPromptGerado("Gere uma pergunta sobre história.");
        assertEquals("Gere uma pergunta sobre história.", pergunta.getPromptGerado());
    }

    @Test
    public void testExplicacaoErro() {
        Pergunta pergunta = new Pergunta("Pergunta teste", 1, Dificuldade.MEDIA, 10);
        pergunta.setExplicacaoErro("Erro ao processar a resposta.");
        assertEquals("Erro ao processar a resposta.", pergunta.getExplicacaoErro());
    }

    @Test
    public void testPontosRecebidos() {
        Pergunta pergunta = new Pergunta("Pergunta teste", 1, Dificuldade.FACIL, 20);
        pergunta.setPontosRecebidos(18);
        assertEquals(18, pergunta.getPontosRecebidos());
    }
}
