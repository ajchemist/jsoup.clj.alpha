(ns user.jsoup.http
  (:require
   [clj-http.client :as http]
   [clj-http.util :as http.util]
   [ring.util.response :as response]
   [user.jsoup :as jsoup]
   ))


(defmethod http/coerce-response-body :jsoup/document
  [req resp]
  (update resp :body
    (fn [body]
      (let [charset (or (:body-charset req) (response/get-charset resp))
            body    (if (string? charset)
                      (String. ^"[B" (http.util/force-byte-array body) ^String charset)
                      (String. ^"[B" (http.util/force-byte-array body)))]
        (jsoup/document body)))))
