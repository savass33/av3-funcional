(ns backend.api
  (:require [compojure.core :refer [defroutes POST GET context]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [dominio.regras :as regras]
            [dominio.portas :as portas]
            [clojure.java.io :as io]))

(defn- responder [corpo]
  {:status 200
   :body corpo})

(defn criar-app [repositorio]
  (defroutes api-routes
    (context "/api" []
      (POST "/usuario" {:keys [body]}
        (portas/salvar-usuario! repositorio body)
        (responder {:mensagem "Usuário registrado com sucesso"}))

      (GET "/usuario" []
        (responder (portas/obter-usuario repositorio)))

      (POST "/refeicao" {:keys [body]}
        (let [extrato (portas/obter-extrato repositorio)
              novo-extrato (regras/processar-refeicao extrato body)]
          (portas/adicionar-evento! repositorio (last novo-extrato))
          (responder {:mensagem "Refeição registrada"})))

      (POST "/exercicio" {:keys [body]}
        (let [extrato (portas/obter-extrato repositorio)
              novo-extrato (regras/processar-exercicio extrato body)]
          (portas/adicionar-evento! repositorio (last novo-extrato))
          (responder {:mensagem "Exercício registrado"})))

      (GET "/extrato" []
        (responder (portas/obter-extrato repositorio)))

      (GET "/saldo" []
        (let [extrato (portas/obter-extrato repositorio)
              meta (get (portas/obter-usuario repositorio) "meta" 2000)]
          (responder {:saldo (regras/consolidar-saldo-diario extrato meta)}))))

    (GET "/" [] (io/resource "public/index.html"))
    (route/resources "/")
    (route/not-found {:error "Rota não encontrada"}))

  (-> api-routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))
