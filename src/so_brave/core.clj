(ns so-brave.core
  (:import [com.github.kristofa.brave Brave$Builder
            ClientRequestAdapter]
           [com.github.kristofa.brave.http
            HttpRequest
            HttpResponse
            HttpServerRequest]
           [io.undertow.server
            HttpServerExchange]))

(defn first-build []
  (let [builder (Brave$Builder.)]
    (. builder build)))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
