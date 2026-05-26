(defproject trab-av3 "0.1.0-SNAPSHOT"
  :description "Calculadora de Calorias - Projeto Acadêmico AV3"
  :url "https://github.com/usuario/trab-av3"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.844"]
                 [org.clojure/test.check "1.1.1"]
                 [compojure "1.7.1"]
                 [ring/ring-json "0.5.1"]
                 [ring/ring-defaults "0.5.0"]
                 [ring/ring-jetty-adapter "1.12.1"]
                 [ring-cors "0.1.13"]
                 [reagent "1.1.0"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [cljs-ajax "0.8.4"]]
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test"]
  :main ^:skip-aot backend.main
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.4.0"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :plugins [[lein-cljsbuild "1.1.8"]]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs" "src/cljc"]
                        :compiler {:output-to "resources/public/js/compiled/trab_av3.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :main frontend.main
                                   :optimizations :none
                                   :pretty-print true}}]})
