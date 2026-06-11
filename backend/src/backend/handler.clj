(ns backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.params :refer [wrap-params]]
            [clj-http.client :as client]
            [cheshire.core :as json]))

(def db (atom {:usuario {} :extrato '()}))

(def api-ninjas-key "MSUBf9CKo1tmPsj6xZEq4bPjUyMF8hwnOsTmTzeA")
(def usda-api-key "B6owfRo3jZ1oHt3lkQi3Gb3rnKXiCLzYk7fHyZId")

;; --- Defesa Acadêmica: APIs Externas (Terceiros) ---

(defn buscar-calorias-alimento-api-externa [alimento quantidade]
  (try
    (let [resposta (client/get "https://api.nal.usda.gov/fdc/v1/foods/search"
                               {:query-params {"query" alimento
                                               "api_key" usda-api-key}
                                :as :json})
          dados (:body resposta)
          foods (:foods dados)]
      (if (empty? foods)
        0
        (let [primeiro-alimento (first foods)
              nutrientes (:foodNutrients primeiro-alimento)
              ;; Filtra o nutriente "Energy" (KCAL) usando filter (função de ordem superior)
              energia (first (filter #(and (= (:nutrientName %) "Energy")
                                           (= (:unitName %) "KCAL"))
                                     nutrientes))
              kcal-por-100g (if energia (:value energia) 0)
              q-float (if (string? quantidade) (Float/parseFloat quantidade) quantidade)]
          (int (* kcal-por-100g (/ q-float 100.0))))))
    (catch Exception e
      (println "[BACKEND] Erro ao consultar USDA API (Alimento):" (.getMessage e))
      0)))

(defn buscar-calorias-exercicio-api-externa [atividade duracao peso-kg]
  (let [peso-lbs (int (* (or peso-kg 72) 2.20462)) ;; Converte kg para lbs (API usa lbs)
        ;; Garante os limites da API (50-500 lbs)
        peso-api (cond (< peso-lbs 50) 50
                       (> peso-lbs 500) 500
                       :else peso-lbs)]
    (if (= api-ninjas-key "COLOQUE_SUA_CHAVE_AQUI")
      (do
        (println "[BACKEND] AVISO: Chave da API Ninjas não configurada. Usando fallback simulado para" atividade)
        (* 8 (if (string? duracao) (Integer/parseInt duracao) duracao)))
      (try
        (let [resposta (client/get "https://api.api-ninjas.com/v1/caloriesburned"
                                   {:query-params {"activity" atividade 
                                                   "duration" duracao
                                                   "weight" peso-api}
                                    :headers {"X-Api-Key" api-ninjas-key}
                                    :as :json})
              dados (:body resposta)]
          (if (empty? dados)
            0
            (:total_calories (first dados))))
        (catch Exception e
          (println "[BACKEND] Erro ao consultar API Ninjas (Exercicio):" (.getMessage e))
          0)))))

;; --- Regras de Negócio e Processamento ---

(defn processar-refeicao [extrato dados]
  (let [alimento (get dados "alimento")
        quantidade (get dados "quantidade")
        data (get dados "data")
        calorias (buscar-calorias-alimento-api-externa alimento quantidade)]
    ;; Utilizamos conj para adicionar o novo mapa à LISTA de extrato
    (conj extrato {:tipo "refeicao" :nome alimento :data data :quantidade quantidade :calorias calorias})))

(defn processar-exercicio [extrato dados peso-kg]
  (let [activity (get dados "atividade")
        duration (get dados "duracao")
        data (get dados "data")
        calorias-gastas (buscar-calorias-exercicio-api-externa activity duration peso-kg)]
    (conj extrato {:tipo "exercicio" :nome activity :data data :duracao duration :calorias (- calorias-gastas)})))

(defn filtrar-por-periodo [extrato periodo]
  (if (or (nil? periodo) (empty? periodo))
    extrato
    ;; Utilização da Higher-Order Function 'filter'
    (filter #(= (:data %) periodo) extrato)))

(defn consolidar-saldo [extrato]
  ;; O saldo é simplesmente a soma das transações (Consumo - Gasto)
  (reduce (fn [acc ev] (+ acc (get ev :calorias 0))) 0 extrato))

(defroutes app-routes
  (POST "/api/usuario" request
    (let [dados (:body request)]
      (swap! db assoc :usuario dados)
      {:status 200 :body {"mensagem" "Dados pessoais salvos com sucesso."}}))

  (GET "/api/usuario" []
    {:status 200 :body (:usuario @db)})

  (POST "/api/refeicao" request
    (let [dados (:body request)]
      (swap! db update :extrato processar-refeicao dados)
      {:status 200 :body {"mensagem" "Refeição registrada via API Externa!"}}))

  (POST "/api/exercicio" request
    (let [dados (:body request)
          peso  (get-in @db [:usuario "peso"])]
      (swap! db update :extrato processar-exercicio dados peso)
      {:status 200 :body {"mensagem" "Exercício registrado via API Externa!"}}))

  (GET "/api/extrato" request
    (let [periodo (get-in request [:query-params "periodo"])
          extrato-filtrado (filtrar-por-periodo (:extrato @db) periodo)]
      {:status 200 :body extrato-filtrado}))

  (GET "/api/saldo" request
    (let [periodo (get-in request [:query-params "periodo"])
          extrato-filtrado (filtrar-por-periodo (:extrato @db) periodo)
          saldo (consolidar-saldo extrato-filtrado)]
      {:status 200 :body {"saldo" (int saldo) "periodo" (or periodo "Total")}}))


      
  (route/not-found "Recurso Inexistente"))

(def app
  (-> app-routes
      (wrap-json-body)
      wrap-json-response
      wrap-params
      (wrap-defaults api-defaults)))
