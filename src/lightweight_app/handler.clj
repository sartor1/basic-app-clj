(ns lightweight-app.handler
 
  (:require [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.params :refer :all]
            [ring.util.response :as resp]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]))

; (selmer.parser/set-resource-path! (clojure.java.io/resource "public"))

(defroutes app-routes
  (GET  "/" [request] (resp/redirect "/home.html"))
  (POST "/sum" request []
    (let [my-json (request :body)]
    (def get-numbers (map (fn [[k v]] (conj v)) my-json))
    {:status 200
     :body    
       (str "Result is " (reduce + get-numbers))}))
  (route/not-found "Not Found")
  (route/resources "/"))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      (middleware/wrap-json-body {:keywords? true})))