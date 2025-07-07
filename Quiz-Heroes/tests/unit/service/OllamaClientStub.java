package unit.service;

import model.Pergunta;
import service.OllamaClient;

public class OllamaClientStub extends OllamaClient {

    @Override
    public String enviarPrompt(String prompt) {
        return "A água ferve a 100°C. Certo ou Errado?";
    }

    @Override
    public String construirContextoHistorico(java.util.List<Pergunta> perguntas) {
        return "Histórico de perguntas anteriores";
    }

    @Override
    public boolean avaliarCertoErradoComContexto(String contexto, Pergunta pergunta, StringBuilder explicacao) {
        // Simula que toda resposta "Certo" está correta
        if ("Certo".equalsIgnoreCase(pergunta.getRespostaDoJogador())) {
            return true;
        } else {
            explicacao.append("A resposta correta era 'Certo'");
            return false;
        }
    }
}
