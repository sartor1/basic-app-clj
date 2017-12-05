(defproject lightweight-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-mock "0.3.2"]
                 [compojure "1.5.1"]]       

  :plugins [[lein-ring "0.9.7"]]
  ; We tell the plugin what handler should be monitored.

  :ring {:handler lightweight-app.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})