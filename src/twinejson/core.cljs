(ns ^:figwheel-always twinejson.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [twinejson.converters :as cnv]
            [twinejson.regex-matchers :as rgx]
            [cljs.core.async :as async :refer [<! >!]]
            [cljs.nodejs :as nodejs]
            [tubax.helpers :as th]
            [cljs-node-io.core :as io :refer [slurp spit]]))

(nodejs/enable-util-print!)

(defn remove-separators 
  "Removes the -> separator from links,
   leaving only the title of the desired passage"

  [raw-links]

  (map
      (fn
        [link]

        (let [links-without-right-separator (map #(last %) (re-seq (rgx/right-arrow-title) (str link)) )
              left-separator-matcher (map #(re-seq (rgx/left-arrow-title) %) links-without-right-separator)
              links-without-left-separator (map #(second %) (first left-separator-matcher))]
              links-without-left-separator))
      raw-links))

(defn find-links 
  "Given the content of a passage,
   will find via regex text matching [[(...)]]
   and return a list of passages"

  [content-of-passage]

  (let [sequence-of-links (re-seq #"\[\[(.+?)\]\]" (first content-of-passage))]
    (map
      (fn
        [link]
        (last link))
     (remove-separators sequence-of-links))))

(defn add-links [parsed-xml]
  (let [passages (th/find-all parsed-xml {:tag :tw-passagedata})
        passages-with-links (map
                              (fn
                                [passage]
                                (assoc
                                  passage
                                  :links (find-links (:content passage))))
                              passages)]
    passages-with-links))

(defn do-conversion [slurped output-path]
  (let [json (cnv/to-json (add-links (cnv/parse-xml slurped)))]
    (go
      (let [[err] (<! (io/aspit output-path json))]
        (if-not err
          (println (str "Successfully written to '" output-path "'"))
          (println "There was an error writing: " err))))))

(defn start-conversion 
  ([input-path] (start-conversion input-path "story.json"))
  ([input-path output-path]

    (go
      (let [[err datastring] (<! (io/aslurp input-path))]
        (if-not err
          (do-conversion datastring output-path)
          (println err))))))

(defn on-js-reload []
  (start-conversion "example-published.html"))

(defn main [& args]
  (if (= (count args) 1)
    (start-conversion (first args))
    (let [one (first args) two (second args)]
      (start-conversion one two))))

(set! *main-cli-fn* main)


