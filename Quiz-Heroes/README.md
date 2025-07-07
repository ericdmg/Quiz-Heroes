# 🧠 Quiz Heroes - Jogo de Perguntas e Respostas com LLM Local via Ollama

Este projeto é um jogo de perguntas e respostas desenvolvido em Java com arquitetura em camadas (MVC). As perguntas são geradas em tempo real por um modelo de linguagem (LLM) executado localmente via **Ollama**.

---

## 📌 Objetivo

Proporcionar uma experiência interativa de quiz com perguntas geradas dinamicamente por uma IA local, sem depender da nuvem. O jogo funciona no terminal (CLI), suporta múltiplos jogadores e fornece um relatório de desempenho ao final.

---

## ⚙️ Requisitos

- **Java 17 ou superior**
- **Git**
- **Ollama** instalado e rodando localmente
- **Modelo `llama3` (ou outro compatível) carregado no Ollama**
- Acesso à internet para o primeiro download do modelo (após isso, roda offline)

---

## 🧭 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/Quiz-Heroes.git
cd Quiz-Heroes
```

> Substitua `seu-usuario` pelo seu nome de usuário no GitHub, se aplicável.

---

### 2. Instale o **Ollama**

- Acesse: https://ollama.com/download
- Instale conforme seu sistema operacional:
  - Windows: Executável `.msi`
  - Linux (Debian/Ubuntu):

    ```bash
    curl -fsSL https://ollama.com/install.sh | sh
    ```

  - macOS:

    ```bash
    brew install ollama
    ```

---

### 3. Baixe o modelo de linguagem

Recomenda-se o `llama3` (modelo da Meta):

```bash
ollama run llama3
```

Aguarde o download completo (pode levar vários minutos).

---

### 4. Compile e execute o projeto Java

Você pode usar uma IDE como IntelliJ ou Eclipse, mas via terminal funciona assim:

```bash
javac -d bin -sourcepath src src/Main.java
java -cp bin Main
```

---

## 🧪 Funcionalidades

- Cadastro de múltiplos jogadores
- Geração dinâmica de perguntas pela LLM local
- Interface via terminal (CLI)
- Controle de pontuação
- Relatório final com classificação e desempenho
- Separação clara entre camada de modelo, controle, visão e serviço de IA

---

## 🧠 Sobre o Ollama e LLM Local

O [Ollama](https://ollama.com) permite executar modelos de linguagem localmente com segurança e privacidade. Este projeto utiliza requisições HTTP para interagir com o Ollama e gerar perguntas de forma contextualizada.

---

## 🧰 Estruturas de Dados Utilizadas

- `List`: Armazena alternativas das perguntas
- `Map`: Guarda jogadores e pontuações
- `Queue` (simulada via lista): Controla ordem de jogadores

---

## ✅ Exemplo de Execução

```bash
Digite o nome do jogador 1: Eric
Digite o nome do jogador 2: Ellen

🧠 Gerando pergunta...
Jogador: Eric
Pergunta: "Qual foi o principal motivo da queda do Império Romano?"
a) Guerras Civis
b) Crise Econômica
c) Invasões Bárbaras
d) Cristianismo
> Sua resposta: c
✅ Correto!

...

🎉 Fim da partida! Ranking:
1. Eric - 3 acertos
2. Ellen - 2 acertos
```

---

## 🧪 Testes

Este projeto inclui:

- **Testes unitários**: localizados em `test/unit`, verificam métodos individuais das classes de serviço e parser;
- **Testes com stubs**: como `OllamaClientStub` e `QuizCLIStub`, usados para simular a interação com a LLM e a CLI, permitindo testes offline;
- **Testes de integração**: localizados em `test/integration`, validam o comportamento conjunto entre `QuizService`, `QuizCLI` e `OllamaClient`.

Os testes são escritos em JUnit e podem ser executados diretamente na IDE ou via terminal.

---

## ✨ Contribuições

Sinta-se livre para abrir issues ou pull requests para melhorar o jogo.

---

## 📜 Licença

Este projeto é de uso acadêmico e pessoal. Direitos reservados a Eric Diego Matozo Gonçalves.
