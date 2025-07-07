package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Jogador {
    private String nome;
    private String id;
    private int pontuacao;
    private List<Pergunta> perguntasRespondidas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return this.pontuacao == jogador.pontuacao && Objects.equals(nome, jogador.nome) && Objects.equals(id, jogador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, id, pontuacao);
    }

    public Jogador(String nome) {
        this.nome = nome;
        this.id = UUID.randomUUID().toString().substring(0, 8); // ID curto e único
        this.pontuacao = 0;
    }

    public String getNome() {
        return this.nome;
    }

    public String getId() {
        return this.id;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacao += pontos;
    }

    public void registrarResposta(Pergunta pergunta) {
        this.perguntasRespondidas.add(pergunta);
    }

    public List<Pergunta> getPerguntasRespondidas() {
        return this.perguntasRespondidas;
    }
}
