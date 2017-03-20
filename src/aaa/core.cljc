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


(defn generate-word [k sep]
  (cond
    (keyword? k) (case k
                   :adjective (rand-nth (shuffle adjectives/list))
                   :animal    (rand-nth (shuffle animals/list))
                   :sep       sep
                   (name k))
    (symbol? k)  (name k)
    :else        k))


(def default-path [:adjective :adjective :animal])


(defn build [final s sep]
  (str final (-> s
                 (generate-word sep)
                 string/trim
                 string/lower-case)))


(defn generate
  "Generates a string based on a `path` of what type of words
   to use, and the `sep` to use. If a `path` is provided with
   no `:sep`, then a `sep` is put between each word.

   Strings, symbols and keywords can be used. 

   Keywords are resolved as follows:
   `:animal`    -> random animal.
   `:adjective` -> random adjective.
   `:sep`       -> given separator or `-`.
    else        -> name of keyword.

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
  (let [path (if (contains? (set path) :sep)
               path
               (butlast (interleave path (repeat sep))))]
    (reduce #(build %1 %2 sep) "" path)))
