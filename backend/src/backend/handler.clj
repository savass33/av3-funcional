(ns backend.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

;; --- Defesa Acadêmica: Estado com Átomos ---
;; A base de dados do sistema, conforme requisito do PDF, é gerenciada
;; integralmente em memória através de um átomo.
(def db (atom {:usuario {} :extrato []}))

;; --- Defesa Acadêmica: Funções Puras (Regras de Negócio) ---
;; Estas funções realizam os cálculos matemáticos do domínio.
;; Não possuem efeitos colaterais.

(defn calcular-alimento [refeicao]
  ;; Extração segura com valores default. 
  ;; Usamos funções de ordem superior (map e reduce) para realizar a matemática, 
  ;; substituindo totalmente qualquer necessidade de iteração manual (loops).
  (let [carbo (get refeicao "carboidratos" 0)
        prot  (get refeicao "proteina" 0)
        gord  (get refeicao "gordura" 0)
        calorias-por-grama [4 4 9]
        valores-ingeridos  [carbo prot gord]]
    (reduce + (map * valores-ingeridos calorias-por-grama))))

(defn calcular-exercicio [exercicio]
  (* (get exercicio "duracao" 0)
     (get exercicio "intensidade" 0)))

(defn processar-refeicao [extrato refeicao]
  (conj extrato (assoc refeicao "tipo" "refeicao" "calorias" (calcular-alimento refeicao))))

(defn processar-exercicio [extrato exercicio]
  (conj extrato (assoc exercicio "tipo" "exercicio" "calorias" (- (calcular-exercicio exercicio)))))

(defn consolidar-saldo [extrato meta]
  ;; O uso da função reduce varre as propriedades puras e as reduz a um saldo.
  ;; Eliminação completa das macros while/for/doseq/loop/dotimes.
  (let [total (reduce (fn [acc ev] (+ acc (get ev "calorias" 0))) 0 extrato)]
    (- total meta)))

;; --- Defesa Acadêmica: Adaptador HTTP (Rotas) ---
;; O Compojure direciona os verbos HTTP para invocar a manipulação
;; segura do Atom (`swap!`) e retornar JSON.

(defroutes app-routes
  (POST "/api/usuario" request
    (let [dados (:body request)]
      (swap! db assoc :usuario dados)
      {:status 200 :body {"mensagem" "Usuario salvo com sucesso."}}))
  
  (GET "/api/usuario" []
    {:status 200 :body (:usuario @db)})
    
  (POST "/api/refeicao" request
    (let [dados (:body request)]
      (swap! db update :extrato processar-refeicao dados)
      {:status 200 :body {"mensagem" "Refeicao registrada!"}}))
      
  (POST "/api/exercicio" request
    (let [dados (:body request)]
      (swap! db update :extrato processar-exercicio dados)
      {:status 200 :body {"mensagem" "Exercicio registrado!"}}))
      
  (GET "/api/extrato" []
    {:status 200 :body (:extrato @db)})
    
  (GET "/api/saldo" []
    (let [meta (get (:usuario @db) "meta" 2000)
          saldo (consolidar-saldo (:extrato @db) meta)]
      {:status 200 :body {"saldo" saldo}}))
      
  (route/not-found "Recurso Inexistente"))

;; Encadeamento de macros de fluxo de dados (Thread-first macro) 
;; para injeção de dependências de Middlewares (JSON parse e defaults de segurança)
(def app
  (-> app-routes
      (wrap-json-body)
      wrap-json-response
      (wrap-defaults api-defaults)))
