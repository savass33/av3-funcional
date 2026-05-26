(ns trab-av3.core
  (:require [reagent.dom :as rdom]
            [trab-av3.ui.componentes.app :refer [painel-principal atualizar-dados!]]))

;; --- Defesa Acadêmica: Ponto de Entrada do Front-end ---
;; Esta função inicializa a aplicação Reagent, montando o componente 
;; raiz no DOM e realizando a carga inicial de dados da API.

(defn ^:export init []
  (atualizar-dados!)
  (rdom/render [painel-principal]
               (.getElementById js/document "app")))
