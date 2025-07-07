package unit.service;

import model.Pergunta;
import org.junit.jupiter.api.Test;
import service.OllamaClient;

import java.util.ArrayList;
import java.util.List;

import static model.Pergunta.Dificuldade.FACIL;
import static org.junit.jupiter.api.Assertions.*;

class OllamaClientTest {

    @Test
    void construirContextoHistorico_deveRetornarTextoFormatado() {
        OllamaClient client = new OllamaClient();

        List<Pergunta> perguntas = new ArrayList<>();

        Pergunta p1 = new Pergunta("O céu é azul?", 0, FACIL, 10);
        p1.setRespostaDoJogador("Certo");

        Pergunta p2 = new Pergunta("A água ferve a 50°C?", 0, FACIL, 10);
        p2.setRespostaDoJogador("Errado");

        perguntas.add(p1);
        perguntas.add(p2);

        String contexto = client.construirContextoHistorico(perguntas);

        assertTrue(contexto.contains("Pergunta: O céu é azul?"));
        assertTrue(contexto.contains("Resposta do jogador: Certo"));
        assertTrue(contexto.contains("Pergunta: A água ferve a 50°C?"));
        assertTrue(contexto.contains("Resposta do jogador: Errado"));
    }

    @Test
    void enviarPrompt_comPromptSimples_deveRetornarTextoQuandoServidorDisponivel() {
        OllamaClient client = new OllamaClient();

        String prompt = "Complete a frase: A Terra é...";
        String resposta = client.enviarPrompt(prompt);

        if (resposta == null) {
            System.out.println("⚠️ Ollama não está rodando localmente. Teste ignorado.");
        } else {
            assertNotNull(resposta);
            assertFalse(resposta.isBlank());
        }
    }

    @Test
    void avaliarCertoErradoComContexto_respondeCorretamente_quandoServidorDisponivel() {
        OllamaClient client = new OllamaClient();

        Pergunta pergunta = new Pergunta("O Sol é uma estrela. Certo ou Errado?", 0, FACIL, 10);
        pergunta.setRespostaDoJogador("Certo");

        StringBuilder explicacao = new StringBuilder();
        String contexto = "";

        boolean correta = client.avaliarCertoErradoComContexto(contexto, pergunta, explicacao);

        if (pergunta.getRespostaGerada() == null) {
            System.out.println("⚠️ Ollama não está rodando localmente. Teste ignorado.");
        } else {
            // Podemos verificar se o parser funcionou
            assertNotNull(pergunta.getRespostaGerada());
            assertTrue(pergunta.getRespostaGerada().contains("correta"));
        }
    }
}
