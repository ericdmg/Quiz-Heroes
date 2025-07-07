package model;

public class Pergunta {
    public enum Dificuldade {
        FACIL, MEDIA, DIFICIL
    }
    private String enunciado;
    private int indiceCorreto;
    private Dificuldade dificuldade;
    private int pontuacao;
    private String respostaGerada;
    private String respostaDoJogador;
    private String explicacaoErro;
    private String promptGerado;
    private int pontosRecebidos;


    public Pergunta(String enunciado, int indiceCorreto, Dificuldade dificuldade, int pontuacao) {
        this.enunciado = enunciado;
        this.indiceCorreto = indiceCorreto;
        this.dificuldade = dificuldade;
        this.pontuacao = pontuacao;
    }

    public String getEnunciado() {
        return this.enunciado;
    }

    public int getIndiceCorreto() {
        return this.indiceCorreto;
    }

    public Dificuldade getDificuldade() {
        return this.dificuldade;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }

    public boolean estaCorreta(int escolhaUsuario) {
        return escolhaUsuario == this.indiceCorreto;
    }

    public String getPromptGerado() {
        return this.promptGerado;
    }

    public String getRespostaGerada() {
        return respostaGerada;
    }

    public String getRespostaDoJogador() {
        return respostaDoJogador;
    }

    public void setRespostaDoJogador(String respostaDoJogador) {
        this.respostaDoJogador = respostaDoJogador;
    }

    public void setPromptGerado(String promptGerado) {
        this.promptGerado = promptGerado;
    }

    public void setRespostaGerada(String resposta) {
        this.respostaGerada = resposta;
    }

    public void setExplicacaoErro(String explicacaoErro) {
        this.explicacaoErro = explicacaoErro;
    }

    public String getExplicacaoErro() {
        return this.explicacaoErro;
    }

    public int getPontosRecebidos() {
        return this.pontosRecebidos;
    }

    public void setPontosRecebidos(int pontosRecebidos) {
        this.pontosRecebidos = pontosRecebidos;
    }
}
