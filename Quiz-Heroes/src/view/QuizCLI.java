package view;

import model.Pergunta;

import java.util.Scanner;

public class QuizCLI {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMensagem(String mensagem) {
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

    public String capturarRespostaTexto() {
        System.out.print("Sua resposta: ");
        return scanner.nextLine();
    }

}
