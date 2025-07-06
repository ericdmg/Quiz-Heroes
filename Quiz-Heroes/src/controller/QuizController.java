package controller;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import service.*;
import view.QuizCLI;

import java.util.List;

public class QuizController {
    private final QuizCLI view;
    private final JogadorService jogadorService;
    private final QuizService quizService;
    private final RodadaService rodadaService;
    private final PerguntaService perguntaService;
    private final OllamaClient ollamaClient;

    public QuizController() {
        this.view = new QuizCLI();
        this.jogadorService = new JogadorService();
        this.ollamaClient = new OllamaClient(this.view);
        this.perguntaService = new PerguntaService(this.view, this.ollamaClient);
        this.quizService = new QuizService(this.perguntaService, ollamaClient);
        this.rodadaService = new RodadaService(this.view, ollamaClient);
    }

    public void iniciarQuiz() {
        this.view.mostrarMensagem("Bem-vindo ao Quiz de Perguntas e Respostas!");

        List<Jogador> jogadores = this.jogadorService.cadastrarJogadores(this.view);
        String tema = this.quizService.sortearTema();
        this.view.mostrarMensagem("Tema sorteado: " + tema);

        List<Pergunta> perguntas = this.quizService.gerarPerguntas(tema);
        Quiz quiz = new Quiz(tema, jogadores, perguntas);

        this.rodadaService.executarRodadas(quiz);
    }
}
