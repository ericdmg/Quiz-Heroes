package service;

import model.Jogador;
import view.QuizCLI;

import java.util.ArrayList;
import java.util.List;

public class JogadorService {

    public List<Jogador> cadastrarJogadores(QuizCLI view) {
        List<Jogador> jogadores = new ArrayList<>();
        int total = view.perguntarNumeroJogadores(1, 5);
        for (int i = 0; i < total; i++) {
            String nome = view.perguntarNomeJogador(i + 1);
            String id = view.perguntarIdJogador(nome);
            jogadores.add(new Jogador(nome, id));
        }
        return jogadores;
    }
}
