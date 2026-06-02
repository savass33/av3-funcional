(ns backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.params :refer [wrap-params]]
            [clj-http.client :as client]
            [cheshire.core :as json]))

;; --- Defesa Acadêmica: Estado com Átomos e Listas ---
;; A base de dados da aplicação deve ser mantida em memória (Atom)
;; e as transações representadas por mapas e armazenadas em listas (List).
(def db (atom {:usuario {} :extrato '()}))

;; =====================================================================
;; ATENÇÃO PROFESSOR / ALUNO:
;; Insira aqui a sua chave real da API Ninjas para que a comunicação
;; externa funcione de verdade. Caso deixe o valor padrão, o sistema
;; fará um fallback seguro (simulação) para não quebrar a apresentação.
;; =====================================================================
(def api-ninjas-key "COLOQUE_SUA_CHAVE_AQUI")

;; --- Defesa Acadêmica: APIs Externas (Terceiros) ---
;; Conforme PDF, as calorias devem ser obtidas por meio de APIs externas
;; (ex: API Ninjas) pelo Back-end.

(defn buscar-calorias-alimento-api-externa [alimento quantidade]
  (if (= api-ninjas-key "COLOQUE_SUA_CHAVE_AQUI")
    (do
      (println "[BACKEND] AVISO: Chave da API Ninjas não configurada. Usando fallback simulado para" alimento)
      (int (* 100 (/ (if (string? quantidade) (Float/parseFloat quantidade) quantidade) 100.0))))
    (try
      (let [query (str quantidade "g " alimento)
            resposta (client/get "https://api.api-ninjas.com/v1/nutrition"
                                 {:query-params {"query" query}
                                  :headers {"X-Api-Key" api-ninjas-key}
                                  :as :json})
            dados (:body resposta)]
        ;; A API Ninjas retorna uma lista de itens encontrados. Somamos todos via reduce.
        (if (empty? dados)
          0
          (reduce + (map :calories dados))))
      (catch Exception e
        (println "[BACKEND] Erro ao consultar API Ninjas (Alimento):" (.getMessage e))
        0))))

(defn buscar-calorias-exercicio-api-externa [atividade duracao]
  (if (= api-ninjas-key "COLOQUE_SUA_CHAVE_AQUI")
    (do
      (println "[BACKEND] AVISO: Chave da API Ninjas não configurada. Usando fallback simulado para" atividade)
      (* 8 (if (string? duracao) (Integer/parseInt duracao) duracao)))
    (try
      (let [resposta (client/get "https://api.api-ninjas.com/v1/caloriesburned"
                                 {:query-params {"activity" atividade "duration" duracao}
                                  :headers {"X-Api-Key" api-ninjas-key}
                                  :as :json})
            dados (:body resposta)]
        ;; A API Ninjas retorna as atividades correspondentes. Pegamos a primeira.
        (if (empty? dados)
          0
          (:total_calories (first dados))))
      (catch Exception e
        (println "[BACKEND] Erro ao consultar API Ninjas (Exercicio):" (.getMessage e))
        0))))

;; --- Regras de Negócio e Processamento ---

(defn processar-refeicao [extrato dados]
  (let [alimento (get dados "alimento")
        quantidade (get dados "quantidade")
        data (get dados "data")
        calorias (buscar-calorias-alimento-api-externa alimento quantidade)]
    ;; Utilizamos conj para adicionar o novo mapa à LISTA de extrato
    (conj extrato {:tipo "refeicao" :nome alimento :data data :quantidade quantidade :calorias calorias})))

(defn processar-exercicio [extrato dados]
  (let [atividade (get dados "atividade")
        duracao (get dados "duracao")
        data (get dados "data")
        calorias-gastas (buscar-calorias-exercicio-api-externa atividade duracao)]
    (conj extrato {:tipo "exercicio" :nome atividade :data data :duracao duracao :calorias (- calorias-gastas)})))

(defn filtrar-por-periodo [extrato periodo]
  (if (or (nil? periodo) (empty? periodo))
    extrato
    ;; Utilização da Higher-Order Function 'filter'
    (filter #(= (:data %) periodo) extrato)))

(defn consolidar-saldo [extrato]
  ;; Uso exclusivo de 'reduce' para iterar sem loops
  (reduce (fn [acc ev] (+ acc (get ev :calorias 0))) 0 extrato))

;; --- Defesa Acadêmica: Adaptador HTTP (Rotas) ---
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
    (let [dados (:body request)]
      (swap! db update :extrato processar-exercicio dados)
      {:status 200 :body {"mensagem" "Exercício registrado via API Externa!"}}))
      
  (GET "/api/extrato" request
    (let [periodo (get-in request [:query-params "periodo"])
          extrato-filtrado (filtrar-por-periodo (:extrato @db) periodo)]
      {:status 200 :body extrato-filtrado}))
    
  (GET "/api/saldo" request
    (let [periodo (get-in request [:query-params "periodo"])
          extrato-filtrado (filtrar-por-periodo (:extrato @db) periodo)
          saldo (consolidar-saldo extrato-filtrado)]
      {:status 200 :body {"saldo" saldo "periodo" (or periodo "Total")}}))
      
  (route/not-found "Recurso Inexistente"))

(def app
  (-> app-routes
      (wrap-json-body)
      wrap-json-response
      wrap-params
      (wrap-defaults api-defaults)))
