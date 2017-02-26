(ns aaa.core
  (:require
   [aaa.lists.adjectives :as adjectives]
   [aaa.lists.animals :as animals]
   [clojure.java.io :as io]
   [clojure.string :as string])
  (:refer-clojure :exclude [shuffle]))

(defn shuffle
  "Same as `clojure.core/shuffle` but ensures that the
   resulting `coll` !== the inputted `coll`."
  [coll]
  (let [coll' (clojure.core/shuffle coll)]
    (if (= coll' coll)
      (recur coll)
      coll')))


(defn word [k]
  (case k
    :adjective (rand-nth (shuffle adjectives/list))
    :animal    (rand-nth (shuffle animals/list))))


(def default-path [:adjective :adjective :animal])


(defn generate
  "Generates a string based on a `path` of what type of words
   to use, and the `sep` to use. 

   Defaults:
   `:path` -> `[:adjective :adjective :animal]`
   `:sep`  -> `-`

   Examples:

   `(generate)`
   => \"wellborn-microbiologic-binturong\"

   `(generate :path [:animal :adjective :animal :animal])`
   => \"boaconstrictor-emancipatory-ankole-kestrel\"

   `(generate :path [:animal :adjective] :sep \"#\")`
   => \"coyote#feisty\""
  [& {:keys [path sep]
      :or {path default-path sep "-"}}]
  (->> (for [k path]
          (-> (word k)
              string/trim
              string/lower-case))
        (string/join (str sep))))
