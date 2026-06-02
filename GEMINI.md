# Memória do Projeto: Calculadora de Calorias (AV3)

Este documento atua como o registro oficial (Project Handbook) das atividades, refatorações e decisões arquiteturais tomadas durante a execução do projeto "Calculadora de Calorias", garantindo aderência absoluta ao edital (PDF).

## 1. Contexto Inicial e Desafio
O projeto iniciou com o objetivo de desenvolver uma aplicação full-stack baseada puramente nos conceitos de **Programação Funcional** abordados nos Capítulos 9 a 13 do livro-texto da disciplina de Clojure (T300).
A primeira versão implementava uma SPA em ClojureScript. No entanto, uma **análise exaustiva do edital (PDF)** revelou discrepâncias significativas em relação aos requisitos estruturais, tecnológicos e de integração exigidos pelo professor.

## 2. A Grande Refatoração (Aderência Estrita ao PDF)
A aplicação original foi **completamente descartada** para ceder lugar a uma nova arquitetura. A refatoração total foi pautada pelos seguintes pilares inegociáveis do documento oficial:

### A. Separação Física (Dois Terminais)
Foi exigida a criação de dois projetos independentes executados via linha de comando (`lein`), sem o uso de interfaces web no cliente.
1.  **Backend (`/backend`)**: Criado usando o template `compojure`. É executado no Terminal 1 via `lein ring server`. Sua responsabilidade é expor uma API REST JSON e gerenciar o estado da aplicação em memória através de um `atom`.
2.  **Frontend (`/frontend`)**: Criado usando o template `app`. É executado no Terminal 2 via `lein run`. Atua como um cliente CLI (Interface de Linha de Comando) interativo que capta os inputs do usuário e realiza as requisições HTTP para a API.

### B. Integração com APIs Externas (Item Crítico)
O edital determinava especificamente (Página 2) que o backend **não** deveria realizar cálculos de macronutrientes internamente, mas sim delegar a obtenção calórica para **APIs de terceiros**.
*   **Decisão Técnica:** Inicialmente implementou-se a *API Ninjas* para ambos. Todavia, detectou-se em testes que a rota de Nutrição (`/v1/nutrition`) foi fechada para contas gratuitas.
*   **Solução Atual e Definitiva:**
    *   **Nutrição (Alimentos):** Migramos a consulta de calorias de alimentos para a **USDA FoodData Central API** (A API oficial de agricultura dos EUA). Utilizou-se a chave pessoal providenciada pelo usuário (`B6owfRo3j...`), consumindo o endpoint `GET /fdc/v1/foods/search`.
    *   **Atividades Físicas (Exercícios):** Mantivemos a consulta na **API Ninjas** (`GET /v1/caloriesburned`), pois esta rota permaneceu funcional e respondeu corretamente durante nossos testes (ex: corrida/running gerou 435kcal).

### C. Estrutura de Dados e Filtros de Transações
*   O Frontend passou a exigir inputs textuais exatos: Altura, Peso, Idade e Sexo (Cadastro).
*   Eventos agora carregam `Data`, `Nome` (Alimento/Atividade), `Quantidade` ou `Duração`.
*   As rotas `/api/extrato` e `/api/saldo` do Backend foram reestruturadas para processar parâmetros de requisição (Query Params). A função de ordem superior `filter` foi aplicada para devolver o histórico de transações **por período (data)** exato, cumprindo o final da página 3 do PDF.

### D. Paradigma Funcional e Erradicação de Laços
Nenhum laço imperativo tradicional existe nesta versão do código:
*   **Laços Proibidos (Eliminados):** `loop`, `for`, `while`, `doseq`, `dotimes`.
*   **Iteração do Menu CLI (Frontend):** Mantido exclusivamente por **Tail Recursion** via chamadas de `(recur)` na cauda das condicionais (`cond`).
*   **Iteração de Coleções:** Impressões de tela no terminal para a leitura do extrato utilizam a função de ordem superior `reduce` para varrer o JSON sem a necessidade de contadores ou mutação de loop local. No backend, a varredura das transações e cálculos também emprega intensamente o `reduce` e `map`.

## 3. Estado Atual do Sistema
A aplicação encontra-se no seu estado final, robusta, amplamente testada (via curls e scripts temporários) e perfeitamente síncrona com os materiais de estudo da disciplina. O relatório técnico (`RELATORIO_TECNICO.md`) já foi atualizado para relatar ao avaliador o uso da integração de múltiplas APIs e os pormenores da CLI interativa.

**Autor e Operador:** Gemini (Assistente CLI)
**Data de Conclusão:** 1 de Junho de 2026