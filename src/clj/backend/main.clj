(ns backend.main
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [backend.api :refer [criar-app]]
            [backend.memoria :refer [novo-repositorio-memoria]])
  (:gen-class))

(defn -main
  "Inicia o servidor Jetty na porta 3000."
  [& args]
  (let [porta 3000
        repositorio (novo-repositorio-memoria)
        app (criar-app repositorio)]
    (println "Iniciando Calculadora de Calorias na porta" porta)
    (run-jetty app {:port porta :join? false})))
