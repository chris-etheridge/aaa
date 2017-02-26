(ns aaa.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string])
  (:refer-clojure :exclude [shuffle]))


(def animals* (slurp (io/resource "lists/animals.csv")))
(def adjectives* (slurp (io/resource "lists/adjectives.csv")))


(def adjectives (string/split adjectives* #","))


(def animals (string/split animals* #","))


(defn shuffle
  "Same as `clojure.core/shuffle` but ensures that the
   resulting `coll` !== the inputted `coll`."
  [coll]
  (let [coll' (shuffle coll)]
    (if (= coll' coll)
      (recur coll)
      coll')))


(defn word [k]
  (case k
    :adjective (rand-nth (shuffle adjectives))
    :animal    (rand-nth (shuffle animals))))


(def default-path [:adjective :adjective :animal])


(defn generate
  [& {:keys [path sep]
      :or {path default-path sep "-"}}]
  (->> (for [k path]
          (-> (word k)
              string/trim
              string/lower-case))
        (string/join (str sep))))
