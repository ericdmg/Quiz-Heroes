package view;


import java.util.Scanner;

public class QuizCLI {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public int perguntarNumeroJogadores(int min, int max) {
        System.out.printf("Quantos jogadores vão participar? (%d-%d): ", min, max);
        int qtd = scanner.nextInt();
        this.scanner.nextLine(); // limpar buffer
        return qtd;
    }

    public String perguntarNomeJogador(int numero) {
        System.out.printf("Digite o nome do jogador %d: ", numero);
        return this.scanner.nextLine();
    }

    public String capturarRespostaTexto() {
        System.out.print("Sua resposta: ");
        return this.scanner.nextLine();
    }

}
