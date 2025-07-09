package unit.service;

import service.OllamaClient;

public class OllamaClientStub extends OllamaClient {

    @Override
    public String enviarPrompt(String prompt) {
        if (prompt.contains("Crie uma afirmação")) {
            return "O céu é azul. Certo ou Errado?";
        } else if (prompt.contains("avalie APENAS a pergunta")) { // <- parte do novo prompt
            return "{\"correta\": true}";
        } else {
            return "[Prompt não reconhecido no stub]";
        }
    }

}
