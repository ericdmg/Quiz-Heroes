package unit.service;

import org.junit.jupiter.api.Test;
import service.OllamaClient;

import static org.junit.jupiter.api.Assertions.*;

class OllamaClientTest {

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
}
