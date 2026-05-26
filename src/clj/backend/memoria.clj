(ns backend.memoria
  (:require [dominio.portas :refer [Repositorio]]))

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
