# Relatório Técnico: Projeto Calculadora de Calorias Functional

Este relatório descreve a **reimplementação completa e exaustiva** da Calculadora de Calorias, desenvolvida para a avaliação AV3 da disciplina de Programação Funcional. Atendendo de forma estrita às novas determinações e ao edital original, a solução descartou a antiga interface web (SPA em ClojureScript) e reconstruiu o projeto sob a ótica de dois projetos Clojure independentes, executados em terminais distintos, operando em rede (API + CLI).

---

## 1. Regras de Negócio Implementadas

A lógica funcional da calculadora baseia-se na consolidação diária do saldo calórico:
1.  **Cadastro Inicial:** Ao iniciar, o sistema solicita Nome, Idade, Gênero, Peso e Altura. O **peso** é o dado biológico fundamental para a precisão das consultas externas.
2.  **Ganhos (Refeição):** Consulta a API USDA para obter as calorias de alimentos com base na quantidade informada.
3. **Perdas (Exercício):** Consulta a API Ninjas para calcular as calorias gastas em atividades físicas. O sistema envia o **peso do usuário** (convertido de kg para lbs) como parâmetro, o que permite que a API devolva um valor de queima calórica personalizado para o porte físico do usuário.
4. **Saldo Consolidado:** O cálculo final é realizado por: `(Calorias Consumidas) - (Calorias Gastas em Exercício)`.


## 2. Paradigma Funcional e Substituição de Laços

Para atender de forma irrestrita às restrições do PDF, os laços iterativos impuros (`loop`, `while`, `for`, `doseq`, `dotimes`) foram erradicados e substituídos por técnicas do paradigma funcional:
*   **Acesso à Memória Sem Efeitos Colaterais no Domínio:** A lógica matemática no backend (incluindo o cálculo da TMB e saldo) é composta unicamente de **Funções Puras** que processam mapas.
*   **Higher-Order Functions:** O `reduce` e `map` foram empregados no Backend para calcular as calorias totais. O `filter` foi usado para extrair o nutriente "Energy" do JSON da USDA e para filtrar transações por data.
*   **Tail Recursion (`recur`):** No Frontend, o controle do menu e a persistência da aplicação no terminal são mantidos exclusivamente via recursão de cauda, garantindo segurança de memória.

## 3. Integração com APIs Externas (Terceiros) e Filtros por Período

Conforme exigência do PDF (página 2), o cálculo das calorias não é efetuado internamente. O Backend funciona como uma ponte (Adapter) que consulta **APIs Externas** para obter a contagem exata. Foram integradas as seguintes APIs:

*   **USDA FoodData Central API:** Utilizada para buscar o valor nutricional (Calorias/Energy) dos alimentos consumidos baseados na pesquisa em inglês (ex: *apple*, *rice*). A chave oficial (`api_key`) já se encontra vinculada no código-fonte.
*   **API Ninjas (Calories Burned):** Utilizada exclusivamente para calcular as calorias gastas em exercícios físicos baseados no tipo de atividade e no tempo de duração (ex: *running*). A chave oficial (`X-Api-Key`) já foi inserida no `handler.clj`.

As requisições `GET` são realizadas de forma nativa utilizando a biblioteca `clj-http.client`. A filtragem complexa de JSONs para extrair a métrica "KCAL" na USDA API foi resolvida utilizando exclusivamente funções de alta-ordem como o `filter`.

*   **Filtros por Período:** Atendendo aos itens 4 e 5 do edital, as rotas `/api/extrato` e `/api/saldo` processam Query Parameters (`?periodo=`). O uso da Higher-Order Function `filter` sobre as datas das transações garante a exibição correta dos eventos diários sem o uso de estruturas condicionais ou laços impuros.

---

## 4. Decisões Arquiteturais e Estrutura de Diretórios

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

## 5. Conclusão

Essa refatoração garantiu não apenas a compatibilidade estrita do sistema aos exemplos em Clojure ensinados em sala e exigidos no edital, mas também separou categoricamente as duas entidades exigidas pelo padrão de sistemas distribuídos e Arquitetura Hexagonal:
*   Um terminal servidor provendo acesso aos dados puros (`Backend`).
*   Um terminal cliente encarregado inteiramente pela interface e I/O com o usuário final (`Frontend`).
