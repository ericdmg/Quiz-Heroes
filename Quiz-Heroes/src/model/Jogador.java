package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Jogador {
    private String nome;
    private String id;
    private int pontuacao;
    private List<Pergunta> perguntasRespondidas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return pontuacao == jogador.pontuacao && Objects.equals(nome, jogador.nome) && Objects.equals(id, jogador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, id, pontuacao);
    }

    public Jogador(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.pontuacao = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public void registrarResposta(Pergunta pergunta) {
        perguntasRespondidas.add(pergunta);
    }

    public List<Pergunta> getPerguntasRespondidas() {
        return perguntasRespondidas;
    }
}
