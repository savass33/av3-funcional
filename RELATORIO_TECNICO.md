# Relatório Técnico: Projeto Calculadora de Calorias Functional

Este relatório descreve a implementação da Calculadora de Calorias, desenvolvida para a avaliação AV3 da disciplina de Programação Funcional. O projeto demonstra a aplicação de conceitos avançados como imutabilidade, funções de ordem superior e a arquitetura hexagonal.

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

## 3. Arquitetura do Sistema (Hexagonal)

O projeto é dividido em camadas para que a "tecnologia" (banco de dados, web) não se misture com a "inteligência" (regras de cálculo).

### A. Núcleo de Domínio (O "Cérebro" Puro)
Localizado em: `src/cljc/trab_av3/dominio/regras_caloricas.cljc`
*   **O que faz:** Contém as fórmulas matemáticas. É escrito em `.cljc` para que funcione tanto no servidor (Java) quanto no navegador (JavaScript).
*   **Principais Funções:**
    *   `calcular-calorias-alimento`: Aplica os multiplicadores (4, 4, 9).
    *   `consolidar-saldo-diario`: Usa o `reduce` para somar todos os eventos e subtrair a meta.

### B. Portas (Os Contratos)
Localizado em: `src/cljc/trab_av3/portas/repositorio.cljc`
*   **O que faz:** Define **como** o sistema deve salvar dados, mas não **onde**. É um "contrato" que diz: "Quem quiser ser o banco de dados deste sistema deve saber salvar-usuario e obter-extrato".

### C. Adaptadores de Saída (Persistência)
Localizado em: `src/clj/trab_av3/adaptadores/saida/repositorio/memoria.clj`
*   **O que faz:** Implementa o contrato das Portas. Aqui usamos um **Atom** (uma caixa segura do Clojure para guardar estados que mudam) para manter os dados na memória do servidor enquanto ele estiver ligado.

### D. Adaptador de Entrada (A API Web)
Localizado em: `src/clj/trab_av3/adaptadores/entrada/api/rotas.clj`
*   **O que faz:** Transforma requisições da internet (JSON) em dados que o Clojure entende. Ele recebe o "clique" do usuário e chama o Domínio para calcular e o Repositório para salvar.

### E. Front-end (A Interface do Usuário)
Localizado em: `src/cljs/trab_av3/ui/componentes/app.cljs`
*   **O que faz:** Desenvolvido com **Reagent**. Cria os formulários e a tabela que o usuário vê. 
*   **Lógica:** Toda vez que o usuário clica em "Salvar", ele envia os dados para a API e o Reagent atualiza a tela automaticamente (reatividade).

### F. Testes Baseados em Propriedades
Localizado em: `test/trab_av3/dominio/propriedades/testes_dominio.clj`
*   **O que faz:** Em vez de testar um exemplo (ex: 2+2=4), ele gera **100 cenários aleatórios** para provar que o saldo calórico nunca falha, não importa a ordem dos eventos.

---

## 4. Conclusão

Este projeto não é apenas uma calculadora, mas um exemplo de **Software Robusto**. Ao separar a inteligência (Domínio) da infraestrutura (Web/Memória), garantimos que o sistema seja fácil de testar, impossível de gerar "bugs de estado" (devido à imutabilidade) e academicamente alinhado com as melhores práticas de Engenharia de Software Funcional.
