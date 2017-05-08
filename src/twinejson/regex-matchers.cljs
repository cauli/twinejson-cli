(ns ^:figwheel-always twinejson.regex-matchers)

(def right-arrow-title #"\[\[(([\w\s\-\>]+)(\-\>)){0,}(.+?)\]\]")
(def left-arrow-title #"([^\<\-\n]+)((\<\-)(.+)){0,}")