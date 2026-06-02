# Relatório Técnico: Projeto Calculadora de Calorias Functional

Este relatório descreve a **reimplementação completa e exaustiva** da Calculadora de Calorias, desenvolvida para a avaliação AV3 da disciplina de Programação Funcional. Atendendo de forma estrita às novas determinações e ao edital original, a solução descartou a antiga interface web (SPA em ClojureScript) e reconstruiu o projeto sob a ótica de dois projetos Clojure independentes, executados em terminais distintos, operando em rede (API + CLI).

---

## 1. Regras de Negócio Implementadas

A lógica funcional da calculadora baseia-se na consolidação diária do saldo calórico:
1.  **Ganhos (Refeição):** Transforma gramas de macronutrientes em calorias ($carbo \times 4 + prot \times 4 + gord \times 9$).
2.  **Perdas (Exercício):** Multiplica o tempo de duração pelo fator de intensidade preenchido.
3.  **Saldo Consolidado:** Subtrai da soma calórica (ganhos - perdas) a meta biológica registrada.

## 2. Paradigma Funcional e Substituição de Laços

Para atender de forma irrestrita às restrições do PDF, os laços iterativos impuros (`loop`, `while`, `for`, `doseq`, `dotimes`) foram erradicados e substituídos por técnicas do paradigma funcional:
*   **Acesso à Memória Sem Efeitos Colaterais no Domínio:** A lógica matemática no backend é composta unicamente de **Funções Puras** que processam mapas.
*   **Higher-Order Functions:** O `reduce` e `map` foram empregados no Backend para calcular as calorias totais e realizar o balanço sem iterar as listas manualmente. O `reduce` também foi usado no Frontend para imprimir a lista de extratos no terminal (evitando o `doseq`).
*   **Tail Recursion (`recur`):** No Frontend, o "loop" contínuo do menu do terminal é mantido exclusivamente pela recursão de cauda através da chamada `(recur)` dentro de um `cond`, sem estourar o limite de pilha (Stack Overflow).

---

## 3. Decisões Arquiteturais e Estrutura de Diretórios

A arquitetura foi inteiramente substituída para isolar completamente o servidor (Backend REST API) da camada de interação visual (Frontend CLI). Ambos os projetos utilizam a linguagem **Clojure nativa (JVM)**.

### A. Backend (`backend/`)
Gerado através do template `compojure`, gerencia o roteamento REST e o banco de dados em memória.
*   **`project.clj`**: Configurado com `compojure`, `ring-defaults` e `ring-json` para a construção nativa da API JSON.
*   **`src/backend/handler.clj`**: Ponto central (e principal) que isola as Regras de Domínio (matemática calórica), a persistência segura baseada no conceito STM com a macro `atom` (`swap!`), e a definição das rotas (`GET` e `POST`) do Compojure.

### B. Frontend (`frontend/`)
Gerado através do template `app`, consistindo em um cliente Desktop/CLI (Interface por Linha de Comando).
*   **`project.clj`**: Importa as bibliotecas `clj-http` (para invocar a REST API) e `cheshire` (para parse do JSON recebido).
*   **`src/frontend/core.clj`**: Arquivo principal com a macro `-main`. Ele orquestra a captura de inputs pelo teclado, envio do JSON sobre HTTP ao servidor e apresentação formatada dos retornos da API. Ele utiliza o `recur` na cauda do `cond` para manter a aplicação viva no terminal.

---

## 4. Conclusão

Essa refatoração garantiu não apenas a compatibilidade estrita do sistema aos exemplos em Clojure ensinados em sala e exigidos no edital, mas também separou categoricamente as duas entidades exigidas pelo padrão de sistemas distribuídos e Arquitetura Hexagonal:
*   Um terminal servidor provendo acesso aos dados puros (`Backend`).
*   Um terminal cliente encarregado inteiramente pela interface e I/O com o usuário final (`Frontend`).
