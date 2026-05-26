(ns trab-av3.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [trab-av3.adaptadores.entrada.api.rotas :refer [criar-app]]
            [trab-av3.adaptadores.saida.repositorio.memoria :refer [novo-repositorio-memoria]])
  (:gen-class))

;; --- Defesa Acadêmica: Composição do Sistema (Arquitetura Hexagonal) ---
;; Este é o ponto de entrada do Back-end. Aqui realizamos a Injeção de Dependência,
;; criando o adaptador de saída (repositório em memória) e passando-o para o
;; adaptador de entrada (aplicativo web). O sistema é montado acoplando as
;; peças definidas nas portas.

(defn -main
  "Inicia o servidor Jetty na porta 3000."
  [& args]
  (let [porta 3000
        repositorio (novo-repositorio-memoria)
        app (criar-app repositorio)]
    (println "Iniciando Calculadora de Calorias na porta" porta)
    (run-jetty app {:port porta :join? false})))
