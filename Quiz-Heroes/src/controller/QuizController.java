package controller;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import service.*;
import view.QuizCLI;

import java.util.List;

public class QuizController {
    private final QuizCLI view;
    //private final JogadorService jogadorService;
    private final QuizService quizService;
    //private final RodadaService rodadaService;
    //private final PerguntaService perguntaService;
    private final OllamaClient ollamaClient;

    public QuizController() {
        this.view = new QuizCLI();
        //this.quiz = new Quiz();
        //this.jogadorService = new JogadorService();
        this.ollamaClient = new OllamaClient(this.view);
        //this.perguntaService = new PerguntaService(this.view, this.ollamaClient);
        this.quizService = new QuizService(this.view, this.ollamaClient);
        //this.rodadaService = new RodadaService(this.view, ollamaClient, perguntaService);
    }

    public void iniciarQuiz() {
        this.view.mostrarMensagem("\n" +
                "   ____  _    _ _____ ______        _    _ ______ _____   ____  ______  _____ \n" +
                "  / __ \\| |  | |_   _|___  /       | |  | |  ____|  __ \\ / __ \\|  ____|/ ____|\n" +
                " | |  | | |  | | | |    / /        | |__| | |__  | |__) | |  | | |__  | (___  \n" +
                " | |  | | |  | | | |   / /         |  __  |  __| |  _  /| |  | |  __|  \\___ \\ \n" +
                " | |__| | |__| |_| |_ / /__        | |  | | |____| | \\ \\| |__| | |____ ____) |\n" +
                "  \\___\\_\\\\____/|_____/_____|       |_|  |_|______|_|  \\_\\\\____/|______|_____/ \n" +
                "                                                                              \n" +
                "                                                                              \n"
        );

        this.view.mostrarMensagem("Bem-vindo ao Quiz de Perguntas e Respostas!");

        String tema = this.quizService.sortearTema();
        this.view.mostrarMensagem("Tema sorteado: " + tema);

        Quiz quiz = new Quiz(); // inicia vazio
        quizService.cadastrarJogadores(quiz);       // preenche jogadores
        quiz.setTema(tema);
        this.quizService.executarRodadas(quiz);
        this.quizService.gerarRelatorioFinal(quiz, this.view);

        this.view.mostrarMensagem("\n" +
                "   ____  ____  _____  _____ _____          _____   ____           _____   ____  _____                _  ____   _____          _____  \n" +
                "  / __ \\|  _ \\|  __ \\|_   _/ ____|   /\\   |  __ \\ / __ \\         |  __ \\ / __ \\|  __ \\              | |/ __ \\ / ____|   /\\   |  __ \\ \n" +
                " | |  | | |_) | |__) | | || |  __   /  \\  | |  | | |  | |        | |__) | |  | | |__) |             | | |  | | |  __   /  \\  | |__) |\n" +
                " | |  | |  _ <|  _  /  | || | |_ | / /\\ \\ | |  | | |  | |        |  ___/| |  | |  _  /          _   | | |  | | | |_ | / /\\ \\ |  _  / \n" +
                " | |__| | |_) | | \\ \\ _| || |__| |/ ____ \\| |__| | |__| |        | |    | |__| | | \\ \\         | |__| | |__| | |__| |/ ____ \\| | \\ \\ \n" +
                "  \\____/|____/|_|  \\_\\_____\\_____/_/    \\_\\_____/ \\____/         |_|     \\____/|_|  \\_\\         \\____/ \\____/ \\_____/_/    \\_\\_|  \\_\\\n" +
                "                                                                                                                                     \n" +
                "                                                                                                                                     \n"
        );
    }
}
