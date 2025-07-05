package model;

import java.util.List;

public class Quiz {
    private String tema;
    private List<Jogador> jogadores;
    private List<Pergunta> perguntas;

    public Quiz(String tema, List<Jogador> jogadores, List<Pergunta> perguntas) {
        this.tema = tema;
        this.jogadores = jogadores;
        this.perguntas = perguntas;
    }

    public String getTema() {
        return tema;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }
}
