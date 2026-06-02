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

(defn get-json [endpoint]
  (try
    (let [res (client/get (str api-url endpoint) {:accept :json :throw-exceptions false})]
      (json/parse-string (:body res) true)) ; true parsed as keywords
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
  (println "    DADOS DO USUARIO      ")
  (println "==========================")
  (let [altura (ler-numero "Altura (cm): ")
        peso   (ler-numero "Peso (kg): ")
        idade  (ler-numero "Idade: ")
        sexo   (ler-texto "Sexo (M/F): ")]
    (post-json "/usuario" {"altura" altura "peso" peso "idade" idade "sexo" sexo})
    (println "-> Dados cadastrados com sucesso na API!")))

(defn consultar-usuario []
  (println "\n==========================")
  (println "    CONSULTAR USUARIO     ")
  (println "==========================")
  (let [user (get-json "/usuario")]
    (if (empty? user)
      (println "Nenhum usuário cadastrado.")
      (do
        (println "Altura:" (:altura user) "cm")
        (println "Peso:" (:peso user) "kg")
        (println "Idade:" (:idade user) "anos")
        (println "Sexo:" (:sexo user))))))

(defn registrar-refeicao []
  (println "\n==========================")
  (println "    REGISTRAR ALIMENTO    ")
  (println "==========================")
  (let [data (ler-texto "Data da refeicao (ex: 26/05/2026): ")
        alimento (ler-texto "Nome do alimento (ex: maca, arroz): ")
        quantidade (ler-numero "Quantidade consumida (em gramas): ")]
    (post-json "/refeicao" {"data" data "alimento" alimento "quantidade" quantidade})
    (println "-> Alimento enviado! A API backend consultou o serviço externo para buscar as calorias.")))

(defn registrar-exercicio []
  (println "\n==========================")
  (println "   REGISTRAR EXERCICIO    ")
  (println "==========================")
  (let [data (ler-texto "Data do exercicio (ex: 26/05/2026): ")
        atividade (ler-texto "Nome da atividade (ex: corrida, caminhada): ")
        duracao (ler-numero "Tempo de duracao (em minutos): ")]
    (post-json "/exercicio" {"data" data "atividade" atividade "duracao" duracao})
    (println "-> Atividade enviada! A API backend consultou o serviço externo para calcular a perda calórica.")))

(defn ver-extrato []
  (println "\n==========================")
  (println "    EXTRATO CALORICO      ")
  (println "==========================")
  (let [extrato (get-json "/extrato")]
    (if (empty? extrato)
      (println "Ainda não existem registros.")
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
  (let [saldo (get-json "/saldo")]
    (when saldo
      (println "Saldo Acumulado: " (:saldo saldo) " calorias"))))

(defn menu []
  (println "\n=== CALCULADORA FUNCIONAL ===")
  (println "1. Cadastrar Dados Pessoais (altura, peso, idade, sexo)")
  (println "2. Consultar Dados Pessoais")
  (println "3. Registrar Consumo de Alimento (Ganho)")
  (println "4. Registrar Atividade Física (Perda)")
  (println "5. Consultar Extrato de Transações")
  (println "6. Consultar Saldo de Calorias")
  (println "7. Sair")
  (print "\nSua escolha: ")
  (flush)
  (let [opcao (read-line)]
    (cond
      (= opcao "1") (do (registrar-usuario) (recur))
      (= opcao "2") (do (consultar-usuario) (recur))
      (= opcao "3") (do (registrar-refeicao) (recur))
      (= opcao "4") (do (registrar-exercicio) (recur))
      (= opcao "5") (do (ver-extrato) (recur))
      (= opcao "6") (do (ver-saldo) (recur))
      (= opcao "7") (println "Saindo...")
      :else (do (println "Opção invalida.") (recur)))))

(defn -main [& args]
  (menu))
