package model;

import java.util.HashMap;
import java.util.Map;

public class Quiz {
    private String tema;
    private Map<String, Jogador> jogadores = new HashMap<>();

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTema() {
        return tema;
    }

    public Map<String, Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(Map<String, Jogador> jogadores) {
        this.jogadores = jogadores;
    }

}
