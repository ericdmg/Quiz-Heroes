package service;

import model.Pergunta;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuizService {
    private final List<String> TEMAS = Arrays.asList(
            "História",
            "Ciência",
            "Geografia",
            "Matemática",
            "Literatura",
            "Tecnologia",
            "Esportes"
    );

    private final Random random = new Random();
    private PerguntaService perguntaService;
    private OllamaClient ollamaClient;

    public QuizService(PerguntaService perguntaService, OllamaClient ollamaClient) {
        this.perguntaService = perguntaService;
        this.ollamaClient = ollamaClient;
    }

    public String sortearTema() {
        int index = random.nextInt(TEMAS.size());
        return TEMAS.get(index);
    }

//    public static List<Pergunta> gerarPerguntas(String tema) {
//        List<Pergunta> todas = new ArrayList<>();
//
//        // Gerar 3 fáceis
//        for (int i = 0; i < 3; i++) {
//            todas.add(new Pergunta(
//                    obterEnunciadoViaOllama(tema, Pergunta.Dificuldade.FACIL),
//                    null, -1,
//                    Pergunta.Dificuldade.FACIL,
//                    10
//            ));
//        }
//
//        // 2 médias
//        for (int i = 0; i < 2; i++) {
//            todas.add(new Pergunta(
//                    obterEnunciadoViaOllama(tema, Pergunta.Dificuldade.MEDIA),
//                    null, -1,
//                    Pergunta.Dificuldade.MEDIA,
//                    20
//            ));
//        }
//
//        // 1 difícil
//        todas.add(new Pergunta(
//                obterEnunciadoViaOllama(tema, Pergunta.Dificuldade.DIFICIL),
//                null, -1,
//                Pergunta.Dificuldade.DIFICIL,
//                30
//        ));
//
//        return todas;
//    }

    private String obterEnunciadoViaOllama(String tema, Pergunta.Dificuldade dificuldade) {
        String prompt = String.format(
                "Gere uma afirmação sobre o tema \"%s\" com dificuldade %s. " +
                        "A afirmação deve terminar com 'Certo ou Errado?'. " +
                        "Exemplo: 'A água ferve a 100 graus Celsius. Certo ou Errado?'\n" +
                        "Responda apenas com a afirmação no formato '... Certo ou Errado?' e nada mais.",
                tema, dificuldade.name().toLowerCase()
        );

        String resposta = this.ollamaClient.enviarPrompt(prompt);
        return resposta != null ? resposta : "[Erro ao obter pergunta]";
    }

    private final List<String> temasDisponiveis = Arrays.asList(
            "História", "Ciência", "Geografia", "Matemática", "Literatura", "Tecnologia", "Esportes"
    );

    public List<Pergunta> gerarPerguntas(String tema) {
        return this.perguntaService.gerarPerguntas(tema);
    }
}
