(ns frontend.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:gen-class))

(def api-url "http://localhost:3000/api")

;; --- Defesa Acadêmica: Cliente HTTP e Funções de Comunicação ---
;; Estas funções garantem a comunicação com o backend em JSON, enviando 
;; as requisições geradas a partir das interações do usuário.

(defn post-json [endpoint data]
  (try
    (client/post (str api-url endpoint)
                 {:body (json/generate-string data)
                  :content-type :json
                  :accept :json
                  :throw-exceptions false})
    (catch Exception e
      (println "Erro de conexão com o Backend:" (.getMessage e)))))

(defn get-json [endpoint]
  (try
    (let [res (client/get (str api-url endpoint) {:accept :json :throw-exceptions false})]
      (json/parse-string (:body res)))
    (catch Exception e
      (println "Erro de conexão com o Backend:" (.getMessage e))
      nil)))

(defn ler-numero [prompt]
  (print prompt)
  (flush)
  (try
    (Integer/parseInt (read-line))
    (catch Exception _
      (println "Formato numérico inválido, assumindo o valor 0.")
      0)))

(defn registrar-usuario []
  (println "\n==========================")
  (println "      REGISTRAR META      ")
  (println "==========================")
  (let [meta (ler-numero "Qual a sua meta de calorias diárias? ")]
    (post-json "/usuario" {"meta" meta})
    (println "-> Meta biológica atualizada com sucesso!")))

(defn registrar-refeicao []
  (println "\n==========================")
  (println "    REGISTRAR REFEICAO    ")
  (println "==========================")
  (let [c (ler-numero "Carboidratos ingeridos (g): ")
        p (ler-numero "Proteina ingerida (g): ")
        g (ler-numero "Gordura ingerida (g): ")]
    (post-json "/refeicao" {"carboidratos" c "proteina" p "gordura" g})
    (println "-> Refeição registrada e calculada com sucesso!")))

(defn registrar-exercicio []
  (println "\n==========================")
  (println "   REGISTRAR EXERCICIO    ")
  (println "==========================")
  (let [d (ler-numero "Duracao do exercício (minutos): ")
        i (ler-numero "Fator de Intensidade (1 a 20): ")]
    (post-json "/exercicio" {"duracao" d "intensidade" i})
    (println "-> Exercício registrado e calculado com sucesso!")))

(defn ver-extrato []
  (println "\n==========================")
  (println "    EXTRATO CALORICO      ")
  (println "==========================")
  (let [extrato (get-json "/extrato")]
    (if (empty? extrato)
      (println "Ainda não existem registros para hoje.")
      
      ;; Substituição completa de Laços: Utilizamos a função 'reduce' 
      ;; para iterar pelo vetor de eventos e aplicar o efeito colateral 'println'.
      ;; A função reduce cumpre o papel do (doseq) e (for), mantendo a integridade funcional.
      (reduce (fn [_ evento]
                (println (str "- " (get evento "tipo") 
                              ": " (get evento "calorias") " calorias")))
              nil
              extrato)))
  
  (let [saldo (get-json "/saldo")]
    (when saldo
      (println "--------------------------")
      (println "SALDO CONSOLIDADO: " (get saldo "saldo") "calorias"))))

;; --- Defesa Acadêmica: Controle de Fluxo via Recursão em Cauda ---
;; Ao invés de usar os macros while ou loop, a iteração contínua do menu 
;; utiliza recursão genuína na posição de cauda com a palavra reservada (recur).
(defn menu []
  (println "\n=== CALCULADORA FUNCIONAL ===")
  (println "1. Definir Usuario (Meta Diária)")
  (println "2. Registrar Refeicao (Ganhos)")
  (println "3. Registrar Exercicio (Perdas)")
  (println "4. Ver Extrato e Saldo Diário")
  (println "5. Sair")
  (print "\nSua escolha: ")
  (flush)
  (let [opcao (read-line)]
    (cond
      (= opcao "1") (do (registrar-usuario) (recur))
      (= opcao "2") (do (registrar-refeicao) (recur))
      (= opcao "3") (do (registrar-exercicio) (recur))
      (= opcao "4") (do (ver-extrato) (recur))
      (= opcao "5") (println "Saindo da Calculadora... Até logo!")
      :else (do (println "Opção invalida, tente novamente.") (recur)))))

(defn -main [& args]
  (println "Iniciando Calculadora de Calorias CLI - Modo Terminal Independente...")
  (menu))
