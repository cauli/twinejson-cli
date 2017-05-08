(ns ^:figwheel-always twinejson.converters
  (:require [tubax.core :refer [xml->clj]]))

(defn clj->json
  [ds]
  (.stringify js/JSON (clj->js ds)))

(defn to-json [input]
  (clj->json input))

(defn parse-xml [xml-string]
  (xml->clj xml-string {:strict false :lowercase true}))