(ns dominio.portas
  "Definição da Porta (Protocolo) de persistência.")

(defprotocol Repositorio
  (salvar-usuario! [this dados-usuario])
  (obter-usuario [this])
  (adicionar-evento! [this evento])
  (obter-extrato [this]))
