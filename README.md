# Calculadora de Calorias Functional (AV3)

Este projeto é uma aplicação de rastreamento calórico desenvolvida como parte da avaliação acadêmica de Programação Funcional (AV3). A solução utiliza **Clojure** no Back-end e **ClojureScript** no Front-end, seguindo rigorosamente os princípios do paradigma funcional e a **Arquitetura Hexagonal**.

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Clojure (JVM) e ClojureScript (Browser).
- **Arquitetura:** Hexagonal (Ports and Adapters) - Capítulos 9-13 (Gregório Melo).
- **Gerenciamento de Dependências:** Leiningen.
- **Front-end:** Reagent (Interface Reativa baseada em React).
- **Back-end:** Ring/Compojure (API REST) e Jetty.
- **Testes:** Testes baseados em propriedades com `clojure.test.check`.

## 🏗️ Arquitetura e Defesa Acadêmica

O projeto foi estruturado para demonstrar o domínio dos conceitos fundamentais da programação funcional:

1.  **Pureza e Imutabilidade:** O núcleo do domínio (`src/cljc/trab_av3/dominio/`) é composto exclusivamente por funções puras. Não existem efeitos colaterais nesta camada.
2.  **Substituição de Laços (Loops):** Conforme exigência do edital, **não são utilizados loops tradicionais** (`loop`, `for`, `while`). Toda a iteração sobre eventos é realizada através de funções de ordem superior como `map`, `reduce` e `filter`.
3.  **Isolamento de Efeitos Colaterais:**
    *   **Adaptadores de Saída:** O estado é persistido em memória através de `atoms` no servidor, isolado da lógica de negócio.
    *   **Adaptadores de Entrada:** A API REST orquestra a comunicação entre o mundo exterior (impuro) e o núcleo de domínio (puro).
4.  **Testes de Propriedade:** Aplicamos a técnica de `Property-Based Testing` para provar matematicamente que o saldo consolidado é consistente, independentemente da ordem em que as refeições e exercícios são registrados.

## 📁 Estrutura do Projeto

```text
trab-av3/
├── src/
│   ├── cljc/ (Código comum/puro)
│   │   └── trab_av3/
│   │       ├── dominio/ (Núcleo Puro)
│   │       └── portas/ (Protocolos/Contratos)
│   ├── clj/ (Back-end)
│   │   └── trab_av3/
│   │       ├── adaptadores/ (Entrada API / Saída Memória)
│   │       └── core.clj (Ponto de entrada do servidor)
│   └── cljs/ (Front-end)
│       └── trab_av3/
│           ├── adaptadores/ (Saída API Cliente)
│           ├── ui/ (Componentes Reagent)
│           └── core.cljs (Ponto de entrada do browser)
└── test/
    └── trab_av3/dominio/propriedades/ (Testes de Propriedade)
```

## 🛠️ Como Executar

### Pré-requisitos
- [Leiningen](https://leiningen.org/) instalado.

### 1. Executar os Testes
```bash
lein test
```

### 2. Compilar o Frontend
```bash
lein cljsbuild once dev
```

### 3. Rodar a Aplicação
```bash
lein run
```

### 4. Acessar
Abra o navegador em: **[http://localhost:3000](http://localhost:3000)**

## 👨‍💻 Autor
Desenvolvido para a disciplina de Programação Funcional (T300) - Semestre 2026.1.
