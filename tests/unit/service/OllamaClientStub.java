package unit.service;

import service.OllamaClient;

public class OllamaClientStub extends OllamaClient {

    @Override
    public String enviarPrompt(String prompt) {
        if (prompt.contains("Crie uma afirmação")) {
            // Simula pergunta gerada pela LLM
            return "O céu é azul. Certo ou Errado?";
        } else if (prompt.contains("avalie a nova afirmação")) {
            // Simula resposta de avaliação: sempre correta
            return "{\"correta\": true}";
        } else {
            return "[Prompt não reconhecido no stub]";
        }
    }
}
