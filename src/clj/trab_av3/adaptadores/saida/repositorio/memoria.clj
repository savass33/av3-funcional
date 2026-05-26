(ns trab-av3.adaptadores.saida.repositorio.memoria
  (:require [trab-av3.portas.repositorio :refer [Repositorio]]))

;; --- Defesa Acadêmica: Gerenciamento de Estado com Átomos (Capítulo 10) ---
;; Conforme o edital, a base de dados é mantida em memória utilizando 'atoms'.
;; Este adaptador de saída implementa o protocolo 'Repositorio', isolando o efeito
;; colateral de mutação de estado do núcleo puro do domínio.

(defrecord RepositorioMemoria [db]
  Repositorio
  (salvar-usuario! [this dados-usuario]
    (swap! db assoc :usuario dados-usuario))

  (obter-usuario [this]
    (:usuario @db))

  (adicionar-evento! [this evento]
    (swap! db update :extrato (fn [extrato] (conj (or extrato []) evento))))

  (obter-extrato [this]
    (or (:extrato @db) [])))

(defn novo-repositorio-memoria []
  (->RepositorioMemoria (atom {:usuario nil :extrato []})))
