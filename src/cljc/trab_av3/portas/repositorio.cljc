(ns trab-av3.portas.repositorio
  "Definição da Porta (Protocolo) de persistência da Calculadora de Calorias.
   Seguindo a Arquitetura Hexagonal (Capítulo 9), esta porta define o contrato
   que os adaptadores de saída devem cumprir.")

(defprotocol Repositorio
  "Protocolo que abstrai a persistência de dados do sistema."
  (salvar-usuario! [this dados-usuario] "Registra os dados pessoais do usuário.")
  (obter-usuario [this] "Recupera os dados pessoais do usuário.")
  (adicionar-evento! [this evento] "Adiciona uma transação (refeição ou exercício) ao extrato.")
  (obter-extrato [this] "Retorna a lista completa de transações registradas."))
