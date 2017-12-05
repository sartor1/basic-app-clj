(ns lightweight-app.handler-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [ring.mock.request :as mock]
            [lightweight-app.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 302))
      (s/includes? (:body response) "Hello World")))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest sent-json-returns-sum-of-numbers
  (testing "json can be sent successfully"
    (let [response (app (-> (mock/request :post "/sum" )
                            (mock/json-body {:x2 2, :x3 5})))]
      (is (= 200 (:status response)))
      (s/includes? (response :body) "7"))))