(ns so-brave.impl.http
  (:import [io.undertow.server HttpServerExchange]
           [io.undertow.util HttpString]
           [com.github.kristofa.brave.http HttpServerRequest HttpClientRequest HttpResponse]
           [java.net URI]))

(deftype UndertowServerRequest [^HttpServerExchange exchange]
  HttpServerRequest
  (^URI getUri [_]
   (URI. (.getRequestURI exchange)))
  (^String getHttpMethod [_]
   (.. exchange getRequestMethod toString))
  (^String getHttpHeaderValue [_ ^String header-name]
    (let [headers (.getRequestHeaders exchange)
          entry (.get headers header-name)]
      (cond (nil? entry) nil
            (.isEmpty entry) nil
            :else  (.. entry iterator next)))))

(defn undertow-server-request [exchange]
  (UndertowServerRequest. exchange))

(deftype UndertowClientRequest [^HttpServerExchange exchange]
  HttpClientRequest
  (^void addHeader [_ ^String header ^String value]
    (let [headers (.getRequestHeaders exchange)]
      (.add headers (HttpString. header) value)))
  (^URI getUri [_]
   (URI. (.getRequestURI exchange)))
  (^String getHttpMethod [_]
   (.. exchange getRequestMethod toString)))

(defn undertow-client-request [exchange]
  (UndertowClientRequest. exchange))

(deftype UndertowClientResponse [^HttpServerExchange exchange]
  HttpResponse
  (getHttpStatusCode [_]
    (.getResponseCode exchange)))

(defn undertow-client-response [exchange]
  (UndertowClientResponse. exchange))
