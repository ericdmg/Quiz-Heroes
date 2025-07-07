package util;

import model.Pergunta;

public class OllamaParser {

    public static String extrairRespostaBruta(String json) {
        try {
            int start = json.indexOf("\"response\":\"");
            if (start == -1) return json;

            start += "\"response\":\"".length();
            StringBuilder sb = new StringBuilder();
            boolean escape = false;

            for (int i = start; i < json.length(); i++) {
                char c = json.charAt(i);

                if (!escape && c == '\"') break;
                if (c == '\\') {
                    escape = !escape;
                    if (!escape) sb.append(c);
                    continue;
                }
                sb.append(c);
                escape = false;
            }

            return sb.toString()
                    .replace("\\n", "") // remove quebras literais
                    .replace("\\r", "")
                    .replace("\\t", "")
                    .strip();
        } catch (Exception e) {
            System.err.println("Erro ao extrair campo response: " + json);
            return json;
        }
    }


    public static boolean avaliarRespostaComExplicacao(String respostaJson, StringBuilder explicacaoOut) {
        boolean correta = respostaJson.contains("\"correta\": true");

        if (!correta) {
            int start = respostaJson.indexOf("\"explicacao\":");
            if (start != -1) {
                start += "\"explicacao\":".length();
                String sub = respostaJson.substring(start).trim();
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

    public static String escapeJson(String text) {
        if (text == null) return "";
        return text
                .replace("\\", "\\\\")  // barra invertida primeiro!
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
