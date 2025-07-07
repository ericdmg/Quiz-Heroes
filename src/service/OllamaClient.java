package service;

import model.Pergunta;
import util.OllamaParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OllamaClient {
    private final String API_URL = "http://localhost:11434/api/generate";
    private final String MODEL = "llama3";

    public  String enviarPrompt(String prompt) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(this.API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                    "{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}",
                    this.MODEL, OllamaParser.escapeJson(prompt)
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

            return OllamaParser.extrairRespostaBruta(response.toString());

        } catch (IOException e) {
            System.err.println("Erro na requisição ao Ollama: " + e.getMessage());
            return null;
        }
    }


}
