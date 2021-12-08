(ns codingame.defibrillators ;; (ns Solution
  (:require [clojure.string :as string])
  (:gen-class))

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn french->double
  [text]
  (Double/parseDouble (string/replace text #"," ".")))

(defn line->defib
  [text]
  (let [columns (string/split text #";")]
    {:name (nth columns 1)
     :lon (french->double (nth columns 4))
     :lat (french->double (nth columns 5))}))

(defn distance
  "All coordinates in degrees"
  [lon1 lat1 lon2 lat2]
  (let [x (* (Math/toRadians (- lon2 lon1))
             (Math/cos (/ (Math/toRadians (+ lat1 lat2)) 2)))
        y (Math/toRadians (- lat2 lat1))]
    (* (Math/sqrt (+ (* x x) (* y y)))) 6371))

(defn -main [& args]
  (let [lon (french->double (read-line))
        lat (french->double (read-line))
        N (Integer/parseInt (read-line))
        defib-lines (repeatedly N read-line)]
    (debug (map #(distance lon lat (:lon %) (:lat %)) (map line->defib defib-lines)))
    (debug (first defib-lines))
    (->> defib-lines
         (map line->defib)
         (map #(assoc % :distance (distance lon lat (:lon %) (:lat %))))
         (reduce (fn [d1 d2] (if (< (:distance d1) (:distance d2)) d1 d2)))
         (:name)
         (output))))
