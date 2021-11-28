(ns codingame.ascii-art
  (:require [clojure.string])
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn add-row
  [font row-number L row]
  (let [font-row (into {} (map-indexed vector (partition L row)))]
    (assoc font row-number font-row)))

(defn format-input
  [input-text]
  (clojure.string/replace (clojure.string/upper-case input-text) #"[^a-zA-Z]" (str (char (inc (int \Z))))))

#_{:clj-kondo/ignore [:unused-binding]}
(defn -main [& args]
  (let [L (Integer/parseInt (read-line))
        H (Integer/parseInt (read-line))
        T (read-line)
        offset (int \A)
        lines (loop [line (read-line)
                     result []]
                (if (>= (count result) H)
                  result
                  (recur (read-line)
                         (conj result line))))
        font (reduce (fn [font [i row]] (add-row font i L row))
                     {}
                     (map-indexed vector lines))
        text (format-input T)] ;; font -> row -> letter, ? is one bigger than Z


    (dotimes [row H]
      ;; (debug row)
      (doseq [char-index (map #(- (int %) offset) (seq text))]
        ;; (debug char-index)
        (print (apply str (get (get font row) char-index))))
      (output ""))))
