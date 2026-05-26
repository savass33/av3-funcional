(ns trab-av3.dominio.regras-caloricas
  "Núcleo de domínio puro para a Calculadora de Calorias.
   Este namespace contém apenas funções puras, sem efeitos colaterais,
   seguindo rigorosamente os princípios de imutabilidade e pureza (Capítulos 9-10).")

;; --- Defesa Acadêmica: Substituição de Laços (Loops) ---
;; Conforme exigido pelo edital, não utilizamos loop/recur, for ou while.
;; Todas as iterações sobre coleções de eventos são realizadas através de
;; funções de ordem superior como map e reduce.

(def fator-carboidrato 4)
(def fator-proteina 4)
(def fator-gordura 9)

(defn calcular-calorias-alimento
  "Calcula o total de calorias de um alimento baseado em seus macronutrientes.
   Fórmula: (carbo * 4) + (prot * 4) + (gord * 9)."
  [{:keys [carboidratos proteina gordura] :or {carboidratos 0 proteina 0 gordura 0}}]
  (+ (* carboidratos fator-carboidrato)
     (* proteina fator-proteina)
     (* gordura fator-gordura)))

(defn calcular-perda-exercicio
  "Calcula a perda calórica de um exercício baseado na duração (minutos) e fator de intensidade."
  [{:keys [duracao-minutos fator-intensidade] :or {duracao-minutos 0 fator-intensidade 0}}]
  (* duracao-minutos fator-intensidade))

(defn processar-refeicao
  "Adiciona um evento de refeição ao extrato.
   Recebe o extrato atual (vetor) e os dados da refeição.
   Retorna um novo vetor com o evento processado."
  [extrato refeicao]
  (let [calorias (calcular-calorias-alimento refeicao)
        evento (assoc refeicao :tipo :refeicao :calorias calorias)]
    (conj extrato evento)))

(defn processar-exercicio
  "Adiciona um evento de exercício ao extrato.
   Recebe o extrato atual (vetor) e os dados do exercício.
   Retorna um novo vetor com o evento processado. As calorias são negativas."
  [extrato exercicio]
  (let [calorias-gastas (calcular-perda-exercicio exercicio)
        evento (assoc exercicio :tipo :exercicio :calorias (- calorias-gastas))]
    (conj extrato evento)))

(defn consolidar-saldo-diario
  "Calcula o saldo calórico final do dia.
   Utiliza a função de ordem superior 'reduce' para somar as calorias de todos os eventos.
   Saldo = (Soma das Calorias Ingeridas - Soma das Calorias Gastas) - Meta Biológica."
  [extrato meta-biologica]
  (let [total-calorias (reduce (fn [acc evento] (+ acc (:calorias evento))) 0 extrato)]
    (- total-calorias meta-biologica)))
