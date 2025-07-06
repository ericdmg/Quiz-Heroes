package service;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import view.QuizCLI;

public class RodadaService {

    private final QuizCLI view;
    private final OllamaClient ollamaClient;

    public RodadaService(QuizCLI view, OllamaClient ollamaClient) {
        this.view = view;
        this.ollamaClient = ollamaClient;
    }

    public void executarRodadas(Quiz quiz) {
        for (Jogador jogador : quiz.getJogadores()) {
            view.mostrarMensagem("\nRodada para: " + jogador.getNome());

            for (Pergunta pergunta : quiz.getPerguntas()) {
                view.mostrarMensagem("\nPergunta: " + pergunta.getEnunciado());
                String resposta = view.capturarRespostaTexto();

                StringBuilder explicacao = new StringBuilder();
                view.mostrarMensagem("🔎 Avaliando resposta...");
                boolean correta = ollamaClient.avaliarCertoErrado(pergunta.getEnunciado(), resposta, explicacao);

                if (correta) {
                    jogador.adicionarPontos(pergunta.getPontuacao());
                    view.mostrarMensagem("✅ Resposta correta! +" + pergunta.getPontuacao() + " pontos.");
                } else {
                    view.mostrarMensagem("❌ Resposta incorreta.");
                    if (!explicacao.isEmpty()) {
                        view.mostrarMensagem("💡 Explicação: " + explicacao.toString());
                    }
                }
            }
        }
    }
}
