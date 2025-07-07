package unit.view;

import view.QuizCLI;

public class QuizCLIStub extends QuizCLI {

    private int contadorNome = 0;
    private final StringBuilder mensagens = new StringBuilder();

    @Override
    public void mostrarMensagem(String mensagem) {
        mensagens.append(mensagem).append("\n"); // Agora armazena a mensagem
    }

    @Override
    public int perguntarNumeroJogadores(int minimo, int maximo) {
        return 2;
    }

    @Override
    public String perguntarNomeJogador(int numeroJogador) {
        contadorNome++;
        return "Jogador" + contadorNome;
    }

    @Override
    public String capturarRespostaTexto() {
        return "Certo"; // resposta padrão para testes
    }

    public boolean contemMensagem(String trecho) {
        return mensagens.toString().contains(trecho);
    }

    public void limparMensagens() {
        mensagens.setLength(0);
    }
}
