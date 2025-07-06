package model;

import java.util.List;

public class Quiz {
    private String tema;
    private List<Jogador> jogadores;

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public String getTema() {
        return tema;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
}
