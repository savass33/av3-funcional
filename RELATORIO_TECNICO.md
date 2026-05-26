# Relatório Técnico: Projeto Calculadora de Calorias Functional

Este relatório descreve a implementação da Calculadora de Calorias, desenvolvida para a avaliação AV3 da disciplina de Programação Funcional. O projeto demonstra a aplicação de conceitos avançados como imutabilidade, funções de ordem superior e a arquitetura hexagonal em uma estrutura simplificada e intuitiva.

---

## 1. Regras de Negócio (O Problema)

O objetivo do sistema é monitorar o balanço energético diário de um usuário. Para isso, o sistema processa dois tipos de eventos:

1.  **Ingestão Alimentar (Ganho):** Calcula as calorias a partir dos macronutrientes:
    *   **Carboidratos:** 1 grama = 4 calorias.
    *   **Proteínas:** 1 grama = 4 calorias.
    *   **Gorduras:** 1 grama = 9 calorias.
2.  **Atividade Física (Perda):** Calcula a perda baseada na **Duração (minutos)** multiplicada por um **Fator de Intensidade**.
3.  **Saldo Consolidado:** Representa o total acumulado (Ganhos - Perdas) subtraído de uma **Meta Biológica** diária (ex: 2000 cal).

---

## 2. Lógica de Programação e Paradigma Funcional

Diferente da programação tradicional (imperativa), onde damos ordens ao computador para mudar valores na memória, este projeto utiliza o **Paradigma Funcional**:

*   **Imutabilidade:** Nenhuma variável muda de valor. Quando "adicionamos" uma refeição, criamos uma *nova* lista contendo a refeição anterior mais a nova.
*   **Substituição de Laços (Loops):** É proibido usar `for` ou `while`. Usamos funções de "Transformação de Coleções":
    *   **`map`:** Para transformar cada item de uma lista.
    *   **`reduce`:** Para "espremer" uma lista e transformá-la em um único valor (ex: somar todas as calorias de uma lista).
*   **Funções Puras:** A lógica de cálculo matemático nunca toca no banco de dados ou na internet. Ela recebe números e retorna números, sendo 100% previsível.

---

## 3. Arquitetura do Sistema e Organização de Arquivos

O projeto utiliza a **Arquitetura Hexagonal**, organizada em pastas intuitivas para facilitar a leitura e manutenção:

### A. Núcleo de Domínio (Inteligência Pura)
Localizado em: `src/cljc/dominio/`
*   **`regras.cljc`**: Contém as fórmulas matemáticas puras. Funciona tanto no servidor quanto no navegador.
*   **`portas.cljc`**: Define o protocolo (contrato) do Repositório, ditando como os dados devem ser salvos sem se preocupar com a tecnologia de banco de dados.

### B. Back-end (Infraestrutura do Servidor)
Localizado em: `src/clj/backend/`
*   **`api.clj`**: O adaptador de entrada web. Gerencia as rotas HTTP e traduz requisições JSON para o sistema.
*   **`memoria.clj`**: O adaptador de saída. Implementa o repositório utilizando um **Atom** do Clojure para persistência em memória.
*   **`main.clj`**: O ponto de partida que liga o servidor Jetty na porta 3000.

### C. Front-end (Interface do Usuário)
Localizado em: `src/cljs/frontend/`
*   **`ui.cljs`**: Componentes visuais construídos com **Reagent**. Gerencia a reatividade da tela.
*   **`cliente.cljs`**: Adaptador de saída que realiza as chamadas para a API do Back-end.
*   **`main.cljs`**: Inicializa a aplicação no navegador.

### D. Garantia de Qualidade (Testes)
Localizado em: `test/dominio/`
*   **`testes_propriedades.clj`**: Usa a biblioteca `test.check` para gerar cenários aleatórios e provar que as regras de cálculo são infalíveis.

---

## 4. Conclusão

A nova estrutura do projeto reflete um design limpo e modular. Ao separar o **Domínio**, o **Backend** e o **Frontend** em pastas distintas e com nomes claros, o projeto demonstra não apenas o domínio técnico da linguagem Clojure, mas também uma compreensão profunda de organização de código e boas práticas de arquitetura de software.
