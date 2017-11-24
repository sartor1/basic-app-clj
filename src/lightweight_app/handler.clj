(ns lightweight-app.handler

  (:require [compojure.core :refer :all]
  			[compojure.core :refer [defroutes GET POST]]
  			[compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [compojure.response :as response]
            [ring.util.response :as ring-resp]
	   		[selmer.parser :as parser]
	   		[ring.middleware.json :as middleware]
	   		[cheshire.core :refer :all]
            )
)
(defn custom-render
  "renders the HTML template located relative to resources/templates"
  [template & [params]]
      (parser/render-file
        template
        (assoc params
          :page template)))


(selmer.parser/set-resource-path! (clojure.java.io/resource "public"))

(defn home-page [request] 
	(custom-render "home.html")
	)

;get request - parse int which user sends
;post api -

;(defapi api
 ; (GET "/hello" []
  ;  :query-params [name :- String]
   ; (ok {:message (str "Hello, " name)})))  

(defroutes app-routes
	(GET "/" [request] (home-page request))
	;(GET "/sum/:id1{[0-9]}:id2{[0-9]}" [id1 id2] (str "<h1>Hello, " id1 " and " id2 "</h1>"))

	(POST "/sum" [json]
		(let [j (parse-string json true)]
			
			(def get-numbers (map (fn [[k v]] (conj v)) j))
			; (parse-string json here instead of j doesn't work - why???
			;(str "<h1>Hello " (+(j :v1) (j :v2)) "</h1>")
			(str "<h1>Result is: " (reduce + get-numbers) "</h1>")

		)
	)

  (route/not-found "Not Found")
  (route/resources "/")
  )

;(def app
 ; (wrap-defaults app-routes site-defaults))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

      ;(middleware/wrap-json-body {:keywords? true})
      ;{:keywords? true} for reading kws as kws not as strings
      ;(middleware/wrap-json-response)
      ;))

;(defn go-bowling? [handler]

;(fn [request]
;(let [request (assoc request :go-bowling? "YES! NOW!")]
;(handler request))))