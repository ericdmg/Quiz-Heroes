package controller;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import service.QuizService;
import view.QuizCLI;
import service.OllamaClient;

import java.util.ArrayList;
import java.util.List;

public class QuizController {
    private List<Jogador> jogadores;
    private QuizCLI view;
    private String temaSorteado;
    private Quiz quiz;

    public QuizController() {
        this.jogadores = new ArrayList<>();
        this.view = new QuizCLI();
    }

    public void iniciarQuiz() {
        view.mostrarMensagem("Bem-vindo ao Quiz de Perguntas e Respostas!");
        this.cadastrarJogadores();
        this.prepararQuiz();
        this.gerarPerguntas();
        this.iniciarRodadas();
    }

    private void gerarPerguntas() {
        List<Pergunta> perguntas = QuizService.gerarPerguntas(quiz.getTema());
        quiz.setPerguntas(perguntas);
    }

    private void cadastrarJogadores() {
        int total = view.perguntarNumeroJogadores(1, 5);
        for (int i = 0; i < total; i++) {
            String nome = view.perguntarNomeJogador(i + 1);
            String id = view.perguntarIdJogador(nome);
            jogadores.add(new Jogador(nome, id));
        }
    }

    private void prepararQuiz() {
        sortearTema();
        this.quiz = new Quiz(temaSorteado, jogadores, null); // perguntas virão depois
    }

    private void sortearTema() {
        temaSorteado = QuizService.sortearTema();
        view.mostrarMensagem("Tema sorteado: " + temaSorteado);
    }

    private void iniciarRodadas() {
        for (Jogador jogador : quiz.getJogadores()) {
            view.mostrarMensagem("\nRodada para: " + jogador.getNome());

            for (Pergunta pergunta : quiz.getPerguntas()) {
                view.mostrarMensagem("\nPergunta: " + pergunta.getEnunciado());
                String resposta = view.capturarRespostaTexto();

                StringBuilder explicacao = new StringBuilder();
                boolean correta = OllamaClient.avaliarCertoErrado(pergunta.getEnunciado(), resposta, explicacao);

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