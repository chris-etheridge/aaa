(ns aaa.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))


(def animals* (slurp (io/resource "lists/animals.csv")))


(def adjectives* (slurp (io/resource "lists/adjectives.csv")))


(def adjectives (string/split adjectives* #","))


(def animals (string/split animals* #","))


(defn random-word [words]
  (let [limit (count words)]
    (nth words (rand-int limit))))


(defn word [k]
  (case k
    :adjective (random-word adjectives)
    :animal    (random-word animals)))


(def default-path [:adjective :adjective :animal])


(defn generate
  ([] (generate default-path))
  ([path] (generate path "-"))
  ([path sep]
   (->> (for [k path]
          (-> (word k)
              string/trim
              string/lower-case))
        (string/join sep))))
