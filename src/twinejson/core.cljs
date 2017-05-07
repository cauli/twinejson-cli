(ns ^:figwheel-always twinejson.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as async :refer [<! >!]]
            [cljs.nodejs :as nodejs]
            [tubax.core :refer [xml->clj]]
            [tubax.helpers :as th]
            [cljs-node-io.core :as io :refer [slurp spit]]
            [cljs-node-io.fs :as fs]))

(nodejs/enable-util-print!)

(defn clj->json
  [ds]
  (.stringify js/JSON (clj->js ds)))

(def story-one "<tw-storydata name=\"Asasa sa sas as as as \" startnode=\"1\" creator=\"Twine\" creator-version=\"2.1.3\" ifid=\"F282FB2F-98BE-4749-8A96-E0C3E80C9B20\" format=\"Harlowe\" format-version=\"2.0.1\" options=\"\" hidden><style role=\"stylesheet\" id=\"twine-user-stylesheet\" type=\"text/twine-css\"></style><script role=\"script\" id=\"twine-user-script\" type=\"text/twine-javascript\"></script><tw-passagedata pid=\"1\" name=\"Title A\" tags=\"tag1 tag-with-many-spaces\" position=\"391,299.5\">Content A

[[banana->b]]
[[apple->banana->sad->d]]
[[eeee]]
[[pineaple<-juice]]
</tw-passagedata><tw-passagedata pid=\"2\" name=\"b\" tags=\"\" position=\"605,300.5\">[[c]]
</tw-passagedata><tw-passagedata pid=\"5\" name=\"c\" tags=\"\" position=\"802,300.5\">Double-click this passage to edit it.</tw-passagedata><tw-passagedata pid=\"4\" name=\"d\" tags=\"\" position=\"605,428.5\">Double-click this passage to edit it.</tw-passagedata><tw-passagedata pid=\"5\" name=\"e\" tags=\"\" position=\"602,100.5\">Double-click this passage to edit it.</tw-passagedata></tw-storydata>

")

(def story-two "<tw-storydata name=\"Asasa sa sas as as as \" startnode=\"1\" creator=\"Twine\" creator-version=\"2.1.3\" ifid=\"F282FB2F-98BE-4749-8A96-E0C3E80C9B20\" format=\"Harlowe\" format-version=\"2.0.1\" options=\"\" hidden><style role=\"stylesheet\" id=\"twine-user-stylesheet\" type=\"text/twine-css\"></style><script role=\"script\" id=\"twine-user-script\" type=\"text/twine-javascript\"></script><tw-passagedata pid=\"1\" name=\"Start\" tags=\"tag1 tag-with-many-spaces\" position=\"391,299.5\">Text

[[b]]
[[d]]
[[e]]
[[f-&gt;g]]</tw-passagedata><tw-passagedata pid=\"2\" name=\"b\" tags=\"\" position=\"636,330.5\">[[c]]
</tw-passagedata><tw-passagedata pid=\"3\" name=\"c\" tags=\"\" position=\"816,197.5\">[[ca]]
[[cb]]
[[cd]]

[[c]]</tw-passagedata><tw-passagedata pid=\"4\" name=\"d\" tags=\"\" position=\"605,503.5\">Yada yada blada blada

[[Start]]</tw-passagedata><tw-passagedata pid=\"5\" name=\"e\" tags=\"\" position=\"530,147.5\">[[e-&gt;a-&gt;c-&gt;ea]]</tw-passagedata><tw-passagedata pid=\"6\" name=\"g\" tags=\"\" position=\"396,511.5\">[[g-&gt;h]]</tw-passagedata><tw-passagedata pid=\"7\" name=\"h\" tags=\"\" position=\"210,507.5\">[[Start]]</tw-passagedata><tw-passagedata pid=\"8\" name=\"ca\" tags=\"\" position=\"1012,247.5\">Double-click this passage to edit it.</tw-passagedata><tw-passagedata pid=\"9\" name=\"cb\" tags=\"\" position=\"802,450.5\">Double-click this passage to edit it.</tw-passagedata><tw-passagedata pid=\"10\" name=\"cd\" tags=\"\" position=\"952,450.5\">Double-click this passage to edit it.</tw-passagedata><tw-passagedata pid=\"11\" name=\"ea\" tags=\"\" position=\"659,31.5\">[[c&lt;-ea]]
[[Link with spaces ]]</tw-passagedata><tw-passagedata pid=\"12\" name=\"Link with spaces \" tags=\"\" position=\"874,20.5\">Double-click this passage to edit it.</tw-passagedata></tw-storydata>
")

(def result (xml->clj story-two {:strict false :lowercase true}))
(def storydata (th/find-all result {:path [:tw-storydata]}))

; (re-find #"\[\[(.+?)\]\]" "[[c]]\n[[d]]")


(defn remove-separators 
  "Removes the -> separator from links,
   leaving only the title of the desired passage"

  [raw-links]

  (map
      (fn
        [link]

        (let [links-without-right-separator (map #(last %)  (re-seq #"\[\[(([\w\s\-\>]+)(\-\>)){0,}(.+?)\]\]" (str link)))
              left-separator-matcher (map #(re-seq #"([^\<\-\n]+)((\<\-)(.+)){0,}" %) links-without-right-separator)
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

(defn add-links [input]
  (let [passages (th/find-all result {:tag :tw-passagedata})
        passages-with-links (map
                              (fn
                                [passage]
                                (assoc
                                  passage
                                  :links (find-links (:content passage))))
                              passages)]

    passages-with-links))

(defn to-json [input]
  (clj->json input))

(defn do-conversion [data output-path]
  (let [json (to-json (add-links data))]
    (go
      (let [[err] (<! (io/aspit output-path (to-json (add-links storydata))))]
        (if-not err
          (println "Successfully written to 'story.json'")
          (println "There was an error writing: " err))))))

(defn start-conversion 
  ([input-path] (start-conversion input-path "story.json"))
  ([input-path output-path]

    (println (to-json (add-links storydata)))

    (go
      (let [[err datastring] (<! (io/aslurp input-path))]
        (if-not err
          (do-conversion datastring output-path)
          (println err))))))

(defn on-js-reload []
  (start-conversion "example.html"))

(defn main [& args]
  (println "args: " args)

  (if (= (count args) 1)
    (start-conversion (first args))
    (let [one (first args) two (second args)]
      (println "one: " one)
      (println "two: " two)
      (start-conversion one two))))

(set! *main-cli-fn* main)


