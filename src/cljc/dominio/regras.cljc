(ns dominio.regras
  "Núcleo de domínio puro para a Calculadora de Calorias.")

(def fator-carboidrato 4)
(def fator-proteina 4)
(def fator-gordura 9)

(defn calcular-calorias-alimento
  [{:keys [carboidratos proteina gordura] :or {carboidratos 0 proteina 0 gordura 0}}]
  (+ (* carboidratos fator-carboidrato)
     (* proteina fator-proteina)
     (* gordura fator-gordura)))

(defn calcular-perda-exercicio
  [{:keys [duracao-minutos fator-intensidade] :or {duracao-minutos 0 fator-intensidade 0}}]
  (* duracao-minutos fator-intensidade))

(defn processar-refeicao
  [extrato refeicao]
  (let [calorias (calcular-calorias-alimento refeicao)
        evento (assoc refeicao :tipo :refeicao :calorias calorias)]
    (conj extrato evento)))

(defn processar-exercicio
  [extrato exercicio]
  (let [calorias-gastas (calcular-perda-exercicio exercicio)
        evento (assoc exercicio :tipo :exercicio :calorias (- calorias-gastas))]
    (conj extrato evento)))

(defn consolidar-saldo-diario
  [extrato meta-biologica]
  (let [total-calorias (reduce (fn [acc evento] (+ acc (:calorias evento))) 0 extrato)]
    (- total-calorias meta-biologica)))
