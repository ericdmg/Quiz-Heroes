import controller.QuizController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        QuizController controller = new QuizController();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Iniciar novo quiz");
            System.out.println("2. Exibir jogadores");
            System.out.println("3. Listar relatórios de quizzes anteriores");
            System.out.println("4. Sair");
            System.out.println("==========================");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Digite um número entre 1 e 4.");
                continue;
            }

            switch (opcao) {
                case 1 -> controller.iniciarNovoQuiz();
                case 2 -> controller.exibirJogadores();
                case 3 -> controller.listarRelatorios();
                case 4 -> {
                    System.out.println("👋 Encerrando a aplicação. Até logo!");
                    continuar = false;
                }
                default -> System.out.println("❌ Opção inválida. Tente novamente.");
            }
        }
    }
}
