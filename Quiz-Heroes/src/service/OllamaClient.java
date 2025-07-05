package service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OllamaClient {
    private static final String API_URL = "http://localhost:11434/api/generate";
    private static final String MODEL = "llama3";

    public static String enviarPrompt(String prompt) {
        return enviarParaOllama(prompt);
    }

    private static String enviarParaOllama(String prompt) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}",
                    MODEL, escapeJson(prompt)
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status != 200) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errorMsg = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMsg.append(line);
                }
                System.err.println("Erro (" + status + "): " + errorMsg);
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }

            return extrairResposta(response.toString());

        } catch (IOException e) {
            System.err.println("Erro na requisição ao Ollama: " + e.getMessage());
            return null;
        }
    }

    public static boolean avaliarCertoErrado(String pergunta, String respostaJogador, StringBuilder explicacaoOut) {
        String prompt = String.format(
                "Pergunta do quiz: \"%s\"\n" +
                        "Resposta do jogador: \"%s\"\n\n" +
                        "Avalie se a resposta do jogador está correta com base na pergunta. A resposta do jogador é uma tentativa de julgar a afirmação como 'Certo' ou 'Errado'. " +
                        "Se o julgamento dele estiver correto, responda com:\n" +
                        "{\"correta\": true}\n" +
                        "Caso contrário, responda com:\n" +
                        "{\"correta\": false, \"explicacao\": \"Explique por que a resposta está errada.\"}\n\n" +
                        "Atenção: avalie a resposta do jogador em relação à veracidade da pergunta. Responda apenas com o JSON.",
                pergunta, respostaJogador
        );

        String resposta = enviarParaOllama(prompt);
        if (resposta == null) return false;

        // Debug opcional
        System.out.println("[DEBUG LLM]: " + resposta);

        boolean correta = resposta.contains("\"correta\": true");

        if (!correta) {
            // Extrai explicação com segurança
            int start = resposta.indexOf("\"explicacao\":");
            if (start != -1) {
                start += "\"explicacao\":".length();
                // Remove aspas extras e espaços
                String sub = resposta.substring(start).trim();
                if (sub.startsWith("\"")) sub = sub.substring(1);
                int end = sub.indexOf("\"");
                if (end != -1) {
                    String explicacao = sub.substring(0, end).replaceAll("[{}]", "").trim();
                    explicacaoOut.append(explicacao);
                }
            }
        }

        return correta;
    }


    private static String extrairResposta(String json) {
        try {
            int start = json.indexOf("\"response\":\"");
            if (start == -1) return json;

            start += "\"response\":\"".length();
            StringBuilder sb = new StringBuilder();
            boolean escape = false;

            for (int i = start; i < json.length(); i++) {
                char c = json.charAt(i);

                if (!escape && c == '\"') break;  // fim da string JSON
                if (c == '\\') {
                    escape = !escape;
                    if (!escape) sb.append(c); // keep single slash
                    continue;
                }
                sb.append(c);
                escape = false;
            }

            return sb.toString().replace("\\n", "\n").trim();
        } catch (Exception e) {
            System.err.println("Erro ao extrair campo response: " + json);
            return json;
        }
    }


    private static String escapeJson(String text) {
        if (text == null) return "";
        return text
                .replace("\\", "\\\\")  // barra invertida primeiro!
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
