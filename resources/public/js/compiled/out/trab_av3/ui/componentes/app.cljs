(ns trab-av3.ui.componentes.app
  (:require [reagent.core :as r]
            [trab-av3.adaptadores.saida.api-cliente.servico :as api]))

;; --- Defesa Acadêmica: Gerenciamento de Estado no Front-end (Capítulo 12) ---
;; Utilizamos átomos de Reagent para gerenciar o estado da interface. 
;; O estado é reativo: qualquer mudança no átomo dispara o re-render dos componentes.
;; A lógica de negócio reside no Back-end, enquanto o Front-end atua como
;; um adaptador de entrada para as interações do usuário.

(defonce estado-app (r/atom {:extrato [] :saldo 0 :usuario {}}))

(defn atualizar-dados! []
  (api/obter-extrato! (fn [ext] (swap! estado-app assoc :extrato ext)))
  (api/obter-saldo! (fn [res] (swap! estado-app assoc :saldo (:saldo res)))))

(defn form-refeicao []
  (let [form (r/atom {:carboidratos 0 :proteina 0 :gordura 0})]
    (fn []
      [:div {:style {:border "1px solid #ccc" :padding "10px" :margin-bottom "10px"}}
       [:h3 "Registrar Refeição"]
       [:label "Carboidratos (g): "]
       [:input {:type "number" :value (:carboidratos @form)
                :on-change #(swap! form assoc :carboidratos (js/parseInt (.. % -target -value)))}]
       [:br]
       [:label "Proteína (g): "]
       [:input {:type "number" :value (:proteina @form)
                :on-change #(swap! form assoc :proteina (js/parseInt (.. % -target -value)))}]
       [:br]
       [:label "Gordura (g): "]
       [:input {:type "number" :value (:gordura @form)
                :on-change #(swap! form assoc :gordura (js/parseInt (.. % -target -value)))}]
       [:br]
       [:button {:on-click #(api/registrar-refeicao! @form atualizar-dados!)} "Salvar"]])))

(defn form-exercicio []
  (let [form (r/atom {:duracao-minutos 0 :fator-intensidade 5})]
    (fn []
      [:div {:style {:border "1px solid #ccc" :padding "10px" :margin-bottom "10px"}}
       [:h3 "Registrar Exercício"]
       [:label "Duração (min): "]
       [:input {:type "number" :value (:duracao-minutos @form)
                :on-change #(swap! form assoc :duracao-minutos (js/parseInt (.. % -target -value)))}]
       [:br]
       [:label "Intensidade (1-20): "]
       [:input {:type "number" :value (:fator-intensidade @form)
                :on-change #(swap! form assoc :fator-intensidade (js/parseInt (.. % -target -value)))}]
       [:br]
       [:button {:on-click #(api/registrar-exercicio! @form atualizar-dados!)} "Salvar"]])))

(defn lista-extrato [eventos]
  [:div
   [:h3 "Extrato de Transações"]
   [:ul
    (map-indexed (fn [idx e]
                   ^{:key idx}
                   [:li (str (if (= (:tipo e) "refeicao") "🍎 Refeição" "💪 Exercício") 
                             ": " (:calorias e) " cal")])
                 eventos)]])

(defn painel-principal []
  [:div {:style {:font-family "sans-serif" :padding "20px"}}
   [:h1 "Calculadora de Calorias Functional"]
   [:h2 {:style {:color (if (< (:saldo @estado-app) 0) "red" "green")}}
    "Saldo Calórico Consolidado: " (:saldo @estado-app) " cal"]
   
   [form-refeicao]
   [form-exercicio]
   [lista-extrato (:extrato @estado-app)]])
