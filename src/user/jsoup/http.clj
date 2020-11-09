(ns user.jsoup.http
  (:require
   [clj-http.client :as http]
   [clj-http.util :as http.util]
   [ring.util.response :as response]
   [user.jsoup :as jsoup]
   ))


(defmethod http/coerce-response-body :jsoup/document
  [{:keys [url] :as req} resp]
  (update resp :body
    (fn [body]
      (let [charset (or (:jsoup.document/charset req) (response/get-charset resp) "UTF-8")]
        (with-open [stream (http.util/force-stream body)]
          (jsoup/document (jsoup/parse-stream stream charset url)))))))
