(ns user.jsoup
  (:refer-clojure :exclude [select])
  (:import
   org.jsoup.Jsoup
   org.jsoup.helper.HttpConnection
   org.jsoup.nodes.Attribute
   org.jsoup.nodes.Attributes
   org.jsoup.nodes.Comment
   org.jsoup.nodes.DataNode
   org.jsoup.nodes.Document
   org.jsoup.nodes.DocumentType
   org.jsoup.nodes.Element
   org.jsoup.nodes.Node
   org.jsoup.nodes.TextNode
   org.jsoup.nodes.XmlDeclaration
   org.jsoup.parser.Parser
   org.jsoup.parser.Tag
   org.jsoup.select.Elements
   ))


(set! *warn-on-reflection* true)


(defprotocol IElement
  (^org.jsoup.nodes.Attributes -attrs [e])
  (^String -classname [e])
  (^String -text [e])
  (^org.jsoup.select.Elements -select [e ^String selector]))


(extend-type Element
  IElement
  (-attrs [e] (.attributes e))
  (-classname [e] (.className e))
  (-text [e] (.text e))
  (-select [e selector] (.select e selector)))


(extend-type Elements
  IElement
  (-text [e] (.text e)))


(defrecord JsoupDocument [^Document document]
  IElement
  (-attrs [_] (.attributes document))
  (-classname [_] (.className document))
  (-text [_] (.text document))
  (-select [_ selector] (.select document selector)))


(defn parse
  ^Document
  [^String html-text]
  (Jsoup/parse html-text))


(defn document
  [^String html-text]
  (->JsoupDocument (parse html-text)))


(defn select
  [e selector]
  (-select e selector))


(defn ^String get-attr
  [e ^String key]
  (.get (-attrs e) key))


(set! *warn-on-reflection* false)
