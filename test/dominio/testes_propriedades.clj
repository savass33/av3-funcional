(ns dominio.testes-propriedades
  (:require [clojure.test :refer [deftest is]]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [dominio.regras :as dominio]))

(def gen-refeicao
  (gen/hash-map :carboidratos (gen/large-integer* {:min 0 :max 500})
                :proteina (gen/large-integer* {:min 0 :max 500})
                :gordura (gen/large-integer* {:min 0 :max 200})))

(def gen-exercicio
  (gen/hash-map :duracao-minutos (gen/large-integer* {:min 0 :max 300})
                :fator-intensidade (gen/large-integer* {:min 0 :max 20})))

(def gen-evento
  (gen/one-of [(gen/fmap #(assoc % :tipo :refeicao) gen-refeicao)
               (gen/fmap #(assoc % :tipo :exercicio) gen-exercicio)]))

(def propriedade-comutativa-do-saldo
  (prop/for-all [eventos (gen/vector gen-evento)
                 meta-biologica (gen/large-integer* {:min 1200 :max 3000})]
    (let [extrato-original (reduce (fn [acc e]
                                     (if (= (:tipo e) :refeicao)
                                       (dominio/processar-refeicao acc e)
                                       (dominio/processar-exercicio acc e)))
                                   [] eventos)
          extrato-embaralhado (shuffle extrato-original)
          saldo-1 (dominio/consolidar-saldo-diario extrato-original meta-biologica)
          saldo-2 (dominio/consolidar-saldo-diario extrato-embaralhado meta-biologica)]
      (= saldo-1 saldo-2))))

(deftest provar-invariante-do-saldo
  (let [resultado (tc/quick-check 100 propriedade-comutativa-do-saldo)]
    (is (:pass? resultado) (str "Falha na propriedade: " (pr-str (:shrunk resultado))))))
