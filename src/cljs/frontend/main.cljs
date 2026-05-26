(ns frontend.main
  (:require [reagent.dom :as rdom]
            [frontend.ui :refer [painel-principal atualizar-dados!]]))

(defn ^:export init []
  (atualizar-dados!)
  (rdom/render [painel-principal]
               (.getElementById js/document "app")))
