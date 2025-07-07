package controller;

import model.Jogador;
import model.Pergunta;
import model.Quiz;
import service.*;
import view.QuizCLI;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizController {
    private final QuizCLI view;
    private final QuizService quizService;
    private final OllamaClient ollamaClient;
    private final Set<Quiz> quizzes;

    public QuizController() {
        this.view = new QuizCLI();
        this.ollamaClient = new OllamaClient();
        this.quizService = new QuizService(this.view, this.ollamaClient);
        this.quizzes = new HashSet<>();
    }

    public void iniciarNovoQuiz() {
        this.view.mostrarMensagem("\n" +
                "   ____  _    _ _____ ______        _    _ ______ _____   ____  ______  _____ \n" +
                "  / __ \\| |  | |_   _|___  /       | |  | |  ____|  __ \\ / __ \\|  ____|/ ____|\n" +
                " | |  | | |  | | | |    / /        | |__| | |__  | |__) | |  | | |__  | (___  \n" +
                " | |  | | |  | | | |   / /         |  __  |  __| |  _  /| |  | |  __|  \\___ \\ \n" +
                " | |__| | |__| |_| |_ / /__        | |  | | |____| | \\ \\| |__| | |____ ____) |\n" +
                "  \\___\\_\\\\____/|_____/_____|       |_|  |_|______|_|  \\_\\\\____/|______|_____/ \n"
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
                "  \\____/|____/|_|  \\_\\_____\\_____/_/    \\_\\_____/ \\____/         |_|     \\____/|_|  \\_\\         \\____/ \\____/ \\_____/_/    \\_\\_|  \\_\\\n"
        );
        this.quizzes.add(quiz);

    }

    public void exibirJogadores() {
        view.mostrarMensagem("\n👥 Jogadores registrados:");
        if (this.quizzes.isEmpty()) {
            view.mostrarMensagem("Nenhum quiz realizado ainda.");
            return;
        }

        for (Quiz quiz : this.quizzes) {
            for (Jogador j : quiz.getJogadores().values()) {
                view.mostrarMensagem("- " + j.getNome() + " (ID: " + j.getId() + ")");
            }
        }
    }

    public void listarRelatorios() {
        view.mostrarMensagem("\n📊 Relatórios de quizzes anteriores:");

        if (this.quizzes.isEmpty()) {
            view.mostrarMensagem("Nenhum quiz realizado ainda.");
            return;
        }

        for (Quiz quiz : this.quizzes) {
            view.mostrarMensagem("▶ Tema: " + quiz.getTema());
            quizService.gerarRelatorioFinal(quiz, view);
        }
    }
}
