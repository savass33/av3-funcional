# Calculadora de Calorias (Clojure API + CLI)

Este projeto atende integralmente à especificação do edital AV3, reimplementando toda a arquitetura em dois projetos Clojure independentes, em estrita obediência aos templates `compojure` e `app` e ao paradigma funcional. A aplicação dispensa tecnologias web no cliente, operando perfeitamente por terminal e REST API.

## 🗂 Estrutura de Diretórios
```text
trab-av3/
├── backend/                  # Projeto 1: REST API
│   ├── project.clj           # Dependências (Compojure, Ring-JSON)
│   └── src/backend/
│       └── handler.clj       # Atom (banco em memória), funções puras e rotas HTTP
│
├── frontend/                 # Projeto 2: Cliente CLI (Desktop)
│   ├── project.clj           # Dependências (clj-http, cheshire)
│   └── src/frontend/
│       └── core.clj          # (-main) Menu iterativo via recur, inputs e invocações HTTP
│
└── RELATORIO_TECNICO.md      # Explicações funcionais, arquiteturais e uso de funções (reduce/recur)
```

## 🚀 Como Executar

A arquitetura exige dois terminais distintos operando simultaneamente, exatamente como especificado:

### Terminal 1 (Inicie o Servidor Backend)
Abra a pasta do backend e inicie a API na porta `3000`:
```bash
cd backend
lein ring server
```
*(Opcional: use `lein ring server-headless` se preferir que ele não tente abrir um navegador web vazio na hora que sobe).*

### Terminal 2 (Inicie a Interface CLI Frontend)
Com o servidor rodando e aguardando chamadas, abra o segundo terminal, entre no frontend e rode o cliente interativo:
```bash
cd frontend
lein run
```

## 🏗 Regras Funcionais Observadas e Respeitadas
-   **Laços (Loops) Abolidos:** Todo o controle de repetição, tanto no preenchimento de I/O do terminal no front, quanto no varrimento das somas no back, utilizam estritamente `(recur)`, `map` e `reduce`. Não existem macros `loop`, `while`, `for`, `doseq` ou `dotimes` neste código.
-   **Imutabilidade:** As informações do projeto nunca são sobrescritas localmente; as funções de processamento instanciam novos valores em memória usando `let`, injetados isoladamente e de forma segura num gerenciador de concorrência (`atom`).
-   **Responsabilidades Separadas:** O Frontend cuida exclusivamente de ler entradas de texto (I/O) e imprimir telas coloridas; ele não sabe calcular calorias. O Backend faz toda a matemática através das *Regras Puras*, gerencia dados em um *Atom*, e devolve as requisições serializadas via JSON HTTP.
