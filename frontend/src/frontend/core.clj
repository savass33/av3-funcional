(ns frontend.core
  (:require [clj-http.client :as client]
            [cheshire.core :as json])
  (:gen-class))

(def api-url "http://localhost:3000/api")

(defn post-json [endpoint data]
  (try
    (client/post (str api-url endpoint)
                 {:body (json/generate-string data)
                  :content-type :json
                  :accept :json
                  :throw-exceptions false})
    (catch Exception e
      (println "Erro de conexão com o Backend:" (.getMessage e)))))

(defn get-json [endpoint params]
  (try
    (let [res (client/get (str api-url endpoint) {:query-params params :accept :json :throw-exceptions false})]
      (json/parse-string (:body res) true))
    (catch Exception e
      (println "Erro de conexão com o Backend:" (.getMessage e))
      nil)))

(defn ler-texto [prompt]
  (print prompt)
  (flush)
  (read-line))

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
  (println "    CADASTRO DE USUARIO   ")
  (println "==========================")
  (let [nome   (ler-texto "Nome completo: ")
        idade  (ler-numero "Idade: ")
        sexo   (ler-texto "Sexo (M/F): ")
        peso   (ler-numero "Peso (kg): ")
        altura (ler-numero "Altura (cm): ")]
    (post-json "/usuario" {"nome" nome "idade" idade "sexo" sexo "peso" peso "altura" altura})
    (println "\n-> Bem-vindo(a)," nome "! Seus dados foram sincronizados com o servidor.")))

(defn consultar-usuario []
  (println "\n==========================")
  (println "    DADOS DO USUARIO      ")
  (println "==========================")
  (let [user (get-json "/usuario" {})]
    (if (empty? user)
      (println "Nenhum usuário cadastrado.")
      (do
        (println "Nome:   " (:nome user))
        (println "Idade:  " (:idade user) "anos")
        (println "Sexo:   " (:sexo user))
        (println "Peso:   " (:peso user) "kg")
        (println "Altura: " (:altura user) "cm")))))

(defn registrar-refeicao []
  (println "\n==========================")
  (println "    REGISTRAR ALIMENTO    ")
  (println "==========================")
  (let [data (ler-texto "Data da refeicao (ex: 26/05/2026): ")
        alimento (ler-texto "Nome do alimento (ex: apple, rice): ")
        quantidade (ler-numero "Quantidade consumida (em gramas): ")]
    (post-json "/refeicao" {"data" data "alimento" alimento "quantidade" quantidade})
    (println "-> Alimento enviado e calorias calculadas via API USDA!")))

(defn registrar-exercicio []
  (println "\n==========================")
  (println "   REGISTRAR EXERCICIO    ")
  (println "==========================")
  (let [data (ler-texto "Data do exercicio (ex: 26/05/2026): ")
        atividade (ler-texto "Nome da atividade (ex: running, walking): ")
        duracao (ler-numero "Tempo de duracao (em minutos): ")]
    (post-json "/exercicio" {"data" data "atividade" atividade "duracao" duracao})
    (println "-> Atividade enviada e calorias calculadas via API Ninjas!")))

(defn ver-extrato []
  (println "\n==========================")
  (println "    EXTRATO CALORICO      ")
  (println "==========================")
  (let [periodo (ler-texto "Digite a data para filtrar (ex: 26/05/2026) ou deixe em branco para ver tudo: ")
        params (if (empty? periodo) {} {"periodo" periodo})
        extrato (get-json "/extrato" params)]
    (if (empty? extrato)
      (println "Ainda não existem registros para este período.")
      (reduce (fn [_ evento]
                (if (= (:tipo evento) "refeicao")
                  (println (str "[" (:data evento) "] Consumo: " (:nome evento) " (" (:quantidade evento) "g) -> +" (:calorias evento) " cal"))
                  (println (str "[" (:data evento) "] Gasto: " (:nome evento) " (" (:duracao evento) "min) -> " (:calorias evento) " cal"))))
              nil
              extrato))))

(defn ver-saldo []
  (println "\n==========================")
  (println "     SALDO CALORICO       ")
  (println "==========================")
  (let [periodo (ler-texto "Digite a data para filtrar (ex: 26/05/2026) ou deixe em branco para ver o saldo total: ")
        params (if (empty? periodo) {} {"periodo" periodo})
        saldo-map (get-json "/saldo" params)]
    (when saldo-map
      (println "Período Consultado: " (:periodo saldo-map))
      (println "SALDO ACUMULADO:    " (:saldo saldo-map) "calorias")
      (cond
        (> (:saldo saldo-map) 0) (println "\n-> Você consumiu mais do que gastou.")
        (< (:saldo saldo-map) 0) (println "\n-> Você gastou mais do que consumiu.")
        :else (println "\n-> Seu saldo está equilibrado.")))))

(defn menu []
  (println "\n=== CALCULADORA FUNCIONAL ===")
  (println "1. Consultar Meus Dados")
  (println "2. Registrar Consumo de Alimento (Ganho de Caloria)")
  (println "3. Registrar Realização de Atividade Física (Perda de Caloria)")
  (println "4. Consultar Extrato de Transações")
  (println "5. Consultar Saldo de Calorias")
  (println "6. Sair")
  (print "\nSua escolha: ")
  (flush)
  (let [opcao (read-line)]
    (cond
      (= opcao "1") (do (consultar-usuario) (recur))
      (= opcao "2") (do (registrar-refeicao) (recur))
      (= opcao "3") (do (registrar-exercicio) (recur))
      (= opcao "4") (do (ver-extrato) (recur))
      (= opcao "5") (do (ver-saldo) (recur))
      (= opcao "6") (println "Saindo...")
      :else (do (println "Opção invalida.") (recur)))))

(defn -main [& args]
  (println "Iniciando Calculadora de Calorias...")
  (registrar-usuario)
  (menu))

