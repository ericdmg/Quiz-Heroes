package view;

import model.Pergunta;

import java.util.List;
import java.util.Scanner;

public class QuizCLI {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMensagem(String mensagem) {
//        System.out.println("\n" +
//                " ________  ___  ___  ___  ________          ___  ___  _______   ________  ________  _______   ________      \n" +
//                "|\\   __  \\|\\  \\|\\  \\|\\  \\|\\_____  \\        |\\  \\|\\  \\|\\  ___ \\ |\\   __  \\|\\   __  \\|\\  ___ \\ |\\   ____\\     \n" +
//                "\\ \\  \\|\\  \\ \\  \\\\\\  \\ \\  \\\\|___/  /|       \\ \\  \\\\\\  \\ \\   __/|\\ \\  \\|\\  \\ \\  \\|\\  \\ \\   __/|\\ \\  \\___|_    \n" +
//                " \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\  \\   /  / /        \\ \\   __  \\ \\  \\_|/_\\ \\   _  _\\ \\  \\\\\\  \\ \\  \\_|/_\\ \\_____  \\   \n" +
//                "  \\ \\  \\\\\\  \\ \\  \\\\\\  \\ \\  \\ /  /_/__        \\ \\  \\ \\  \\ \\  \\_|\\ \\ \\  \\\\  \\\\ \\  \\\\\\  \\ \\  \\_|\\ \\|____|\\  \\  \n" +
//                "   \\ \\_____  \\ \\_______\\ \\__\\\\________\\       \\ \\__\\ \\__\\ \\_______\\ \\__\\\\ _\\\\ \\_______\\ \\_______\\____\\_\\  \\ \n" +
//                "    \\|___| \\__\\|_______|\\|__|\\|_______|        \\|__|\\|__|\\|_______|\\|__|\\|__|\\|_______|\\|_______|\\_________\\\n" +
//                "          \\|__|                                                                                 \\|_________|\n" +
//                "                                                                                                            \n" +
//                "                                                                                                            \n");
        System.out.println(mensagem);
    }

    public int perguntarNumeroJogadores(int min, int max) {
        System.out.printf("Quantos jogadores vão participar? (%d-%d): ", min, max);
        int qtd = scanner.nextInt();
        scanner.nextLine(); // limpar buffer
        return qtd;
    }

    public String perguntarNomeJogador(int numero) {
        System.out.printf("Digite o nome do jogador %d: ", numero);
        return scanner.nextLine();
    }

    public String perguntarIdJogador(String nome) {
        System.out.printf("Digite um ID único para o jogador %s: ", nome);
        return scanner.nextLine();
    }

    public void mostrarPergunta(Pergunta pergunta) {
        System.out.println("\n" + pergunta.getEnunciado());
        List<String> alternativas = pergunta.getAlternativas();
        for (int i = 0; i < alternativas.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, alternativas.get(i));
        }
    }

    public int cronometroComPergunta(Pergunta pergunta) {
        final int TEMPO_LIMITE = 60;
        final int[] resposta = { -1 };

        Thread entrada = new Thread(() -> {
            System.out.print("Digite sua resposta (1-" + pergunta.getAlternativas().size() + "): ");
            Scanner s = new Scanner(System.in);
            try {
                resposta[0] = s.nextInt() - 1; // converte para índice 0-based
            } catch (Exception ignored) {}
        });

        entrada.start();

        for (int i = TEMPO_LIMITE; i >= 0; i--) {
            System.out.print("\r⏳ Tempo restante: " + i + "s   ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            if (!entrada.isAlive()) break;
        }

        if (entrada.isAlive()) {
            entrada.interrupt();
            System.out.println("\n⏱️ Tempo esgotado!");
        }

        return resposta[0];
    }
    public String capturarRespostaTexto() {
        System.out.print("Sua resposta: ");
        return scanner.nextLine();
    }

}
