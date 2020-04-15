(ns webkv.core
  (:require [org.httpkit.server :as http]
            [ring.middleware.defaults
             :refer [wrap-defaults
                     api-defaults]]
            [bidi.ring :refer [make-handler]])
  (:gen-class))

;; We want to be sure none of our calls relies on reflection
(set! *warn-on-reflection* 1)

;; This is where we store our data
(def ^String tmpdir
  (System/getProperty "java.io.tmpdir"))

;; That's how we find a file given a key.
;; Keys must match the given pattern.
(defn file [^String key]
  {:pre [(re-matches #"^[A-Za-z-]+$" key)]}
  (java.io.File. tmpdir key))

;; Here we handle GET requests. We just read from a file.
(defn get-handler
  [{:keys [params]}]
  {:body (str (slurp (file (params :key))) "\n")})

;; This is our PUT request handler.
;; Given a key and and a value we write to a file.
(defn put-handler
  [{:keys [params]}]
  (let [val (params :val)]
    (spit (file (params :key)) val)
    {:body (str val "\n"), :status 201}))

;; Here's the routing tree of our application.
;; We pick the handler depending on the HTTP
;; verb. On top of that we add an extra middleware
;; to parse data sent in requests.
(def handler
  (-> ["/kv/" {[:key] {:get #'get-handler
                       :put #'put-handler}}]
      (make-handler)
      (wrap-defaults api-defaults)))

;; Finally, we've got all we need to expose
;; our handler over HTTP.
(defn -main []
  (http/run-server handler
                   {:port 8080})
  (println "Server running at: http://localhost:8080"))
