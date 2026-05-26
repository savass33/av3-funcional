(ns trab-av3.adaptadores.entrada.api.rotas
  (:require [compojure.core :refer [defroutes POST GET context]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [trab-av3.dominio.regras-caloricas :as dominio]
            [trab-av3.portas.repositorio :as repo]
            [clojure.java.io :as io]))

(defn- responder [corpo]
  {:status 200
   :body corpo})

(defn criar-app [repositorio]
  (defroutes api-routes
    (context "/api" []
      (POST "/usuario" {:keys [body]}
        (repo/salvar-usuario! repositorio body)
        (responder {:mensagem "Usuário registrado com sucesso"}))

      (GET "/usuario" []
        (responder (repo/obter-usuario repositorio)))

      (POST "/refeicao" {:keys [body]}
        (let [extrato (repo/obter-extrato repositorio)
              novo-extrato (dominio/processar-refeicao extrato body)]
          (repo/adicionar-evento! repositorio (last novo-extrato))
          (responder {:mensagem "Refeição registrada"})))

      (POST "/exercicio" {:keys [body]}
        (let [extrato (repo/obter-extrato repositorio)
              novo-extrato (dominio/processar-exercicio extrato body)]
          (repo/adicionar-evento! repositorio (last novo-extrato))
          (responder {:mensagem "Exercício registrado"})))

      (GET "/extrato" []
        (responder (repo/obter-extrato repositorio)))

      (GET "/saldo" []
        (let [extrato (repo/obter-extrato repositorio)
              meta (get (repo/obter-usuario repositorio) "meta" 2000)]
          (responder {:saldo (dominio/consolidar-saldo-diario extrato meta)}))))

    ;; Servir o index.html na raiz
    (GET "/" [] (io/resource "public/index.html"))
    ;; Servir outros recursos estáticos
    (route/resources "/")
    (route/not-found {:error "Rota não encontrada"}))

  (-> api-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
