package unit.util;

import org.junit.jupiter.api.Test;
import util.OllamaParser;

import static org.junit.jupiter.api.Assertions.*;

class OllamaParserTest {

    @Test
    void extrairRespostaBruta_retornaSomenteTextoQuandoJsonValido() {
        String json = "{\"response\":\"A água ferve a 100°C.\\\\n\",\"done\":true}";
        String resultado = OllamaParser.extrairRespostaBruta(json);
        assertEquals("A água ferve a 100°C.", resultado);
    }


    @Test
    void extrairRespostaBruta_jsonSemCampoResponse_retornaJsonInteiro() {
        String json = "{\"algumaOutraChave\":\"valor\"}";
        String resultado = OllamaParser.extrairRespostaBruta(json);
        assertEquals(json, resultado);
    }

    @Test
    void avaliarRespostaComExplicacao_respostaCorreta_trueSemExplicacao() {
        String json = "{\"correta\": true}";
        StringBuilder explicacao = new StringBuilder();
        boolean resultado = OllamaParser.avaliarRespostaComExplicacao(json, explicacao);

        assertTrue(resultado);
        assertEquals(0, explicacao.length());
    }

    @Test
    void avaliarRespostaComExplicacao_respostaIncorretaComExplicacao_extraiExplicacao() {
        String json = "{\"correta\": false, \"explicacao\": \"A resposta correta era 'Errado'.\"}";
        StringBuilder explicacao = new StringBuilder();
        boolean resultado = OllamaParser.avaliarRespostaComExplicacao(json, explicacao);

        assertFalse(resultado);
        assertTrue(explicacao.toString().contains("A resposta correta era"));
    }

    @Test
    void avaliarRespostaComExplicacao_jsonSemExplicacao_naoExplodeNemAdicionaNada() {
        String json = "{\"correta\": false}";
        StringBuilder explicacao = new StringBuilder();
        boolean resultado = OllamaParser.avaliarRespostaComExplicacao(json, explicacao);

        assertFalse(resultado);
        assertEquals(0, explicacao.length());
    }

    @Test
    void escapeJson_caracteresEspeciais_saoEscapadosCorretamente() {
        String original = "Texto com \"aspas\", barra \\ e quebra\nlinha";
        String esperado = "Texto com \\\"aspas\\\", barra \\\\ e quebra\\nlinha";

        String resultado = OllamaParser.escapeJson(original);
        assertEquals(esperado, resultado);
    }

    @Test
    void escapeJson_textoNulo_retornaVazio() {
        String resultado = OllamaParser.escapeJson(null);
        assertEquals("", resultado);
    }
}
