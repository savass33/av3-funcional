(ns trab-av3.adaptadores.saida.api-cliente.servico
  (:require [ajax.core :refer [GET POST]]))

;; --- Defesa Acadêmica: Adaptadores de Saída no Frontend (ClojureScript) ---
;; Este adaptador abstrai as chamadas HTTP para o Back-end. No frontend, 
;; as chamadas de rede são efeitos colaterais que devem ser isolados da 
;; lógica de visualização (Reagent).

(def api-url "http://localhost:3000/api")

(defn registrar-usuario! [dados callback-sucesso]
  (POST (str api-url "/usuario")
        {:params dados
         :format :json
         :handler callback-sucesso}))

(defn registrar-refeicao! [dados callback-sucesso]
  (POST (str api-url "/refeicao")
        {:params dados
         :format :json
         :handler callback-sucesso}))

(defn registrar-exercicio! [dados callback-sucesso]
  (POST (str api-url "/exercicio")
        {:params dados
         :format :json
         :handler callback-sucesso}))

(defn obter-extrato! [callback-sucesso]
  (GET (str api-url "/extrato")
       {:response-format :json
        :keywords? true
        :handler callback-sucesso}))

(defn obter-saldo! [callback-sucesso]
  (GET (str api-url "/saldo")
       {:response-format :json
        :keywords? true
        :handler callback-sucesso}))
