(ns so-brave.impl.handler
  (:import [io.undertow.server HttpHandler HttpServerExchange]
           [com.github.kristofa.brave Brave$Builder]
           [com.github.kristofa.brave.http DefaultSpanNameProvider HttpServerRequestAdapter])
  (:require [so-brave.impl.http :as http]))


(deftype UndertowBraveHandler [^Brave$Builder builder]
  HttpHandler
  (^void handleRequest [_ ^HttpServerExchange exchange]
    (let [server-request-interceptor (.serverRequestInterceptor builder)
          server-response-interceptor (.serverResponseInterceptor builder)
          request-adapter (HttpServerRequestAdapter. (http/undertow-server-request exchange)
                                                     (DefaultSpanNameProvider.))]
      (.handle server-request-interceptor request-adapter))))

