(ns codingame.unary
  (:require [clojure.string :as string])
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn pad-string
  [text prefix target-length]
  (str (apply str (repeat (- target-length (count text)) prefix)) text))

(defn text->binary
  [text]
  (string/join (map #(pad-string (Long/toBinaryString %) "0" 7) (map int text))))

(defn repetitions
  "Change to vectors of character literal and count"
  ([text]
   (repetitions [] text))
  ([previous-repeats text]
   (if (empty? text)
     previous-repeats
     (let [character (first text)
           repeats (count (take-while #(= character %) text))]
       (recur (conj previous-repeats [character repeats])
              (drop repeats text))))))

(defn encode
  [repeats]
  (string/join " " (map (fn [[character number]]
                          (str (if (= character \1) "0" "00")
                               " "
                               (apply str (repeat number "0"))))
                        repeats)))

(defn -main [& args]
  (let [MESSAGE (read-line)]

    ; (debug "Debug messages...")

    ; Write answer to stdout
    (output (encode (repetitions (text->binary MESSAGE))))))
