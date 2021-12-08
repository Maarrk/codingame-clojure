(ns codingame.horse-racing-duals ;; (ns Solution
  (:require [clojure.string :as str])
  (:gen-class))

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn -main [& args]
  (let [N (Integer/parseInt (read-line))
        lines (repeatedly N read-line)]
    (as-> lines v
      (map #(Integer/parseInt %) v)
      (sort v)
      (map - v (rest v))
      (apply max v)
      (* v -1)
      (output v)
    )))
