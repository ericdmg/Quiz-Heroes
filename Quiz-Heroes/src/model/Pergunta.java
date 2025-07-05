package model;

import java.util.List;

public class Pergunta {
    public enum Dificuldade {
        FACIL, MEDIA, DIFICIL
    }

    private String enunciado;
    private List<String> alternativas;
    private int indiceCorreto;
    private Dificuldade dificuldade;
    private int pontuacao;

    public Pergunta(String enunciado, List<String> alternativas, int indiceCorreto, Dificuldade dificuldade, int pontuacao) {
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.indiceCorreto = indiceCorreto;
        this.dificuldade = dificuldade;
        this.pontuacao = pontuacao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }

    public int getIndiceCorreto() {
        return indiceCorreto;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public boolean estaCorreta(int escolhaUsuario) {
        return escolhaUsuario == indiceCorreto;
    }
}
