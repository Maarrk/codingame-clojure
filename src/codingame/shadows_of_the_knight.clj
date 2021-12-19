(ns codingame.shadows-of-the-knight ;; (ns Player
  (:require [clojure.string :as string])
  (:gen-class))

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn shrink-area
  [[x y] area direction]
  (let [[dir-x dir-y] (case direction
                        "U"  [0 -1]
                        "UR" [1 -1]
                        "R"  [1  0]
                        "DR" [1  1]
                        "D"  [0  1]
                        "DL" [-1  1]
                        "L"  [-1  0]
                        "UL" [-1 -1])]
    (as-> area a
      (if (>= dir-x 0) (assoc a :xmin (+ x dir-x)) a)
      (if (<= dir-x 0) (assoc a :xmax (+ x dir-x)) a)
      (if (>= dir-y 0) (assoc a :ymin (+ y dir-y)) a)
      (if (<= dir-y 0) (assoc a :ymax (+ y dir-y)) a))))

(defn centerpoint
  [arena]
  [(int (/ (+ (:xmin arena) (:xmax arena)) 2))
   (int (/ (+ (:ymin arena) (:ymax arena)) 2))])

(defn -main
  #_{:clj-kondo/ignore [:unused-binding]} [& args]
  (let [; W: width of the building.
        ; H: height of the building.
        [W H] (map #(Integer/parseInt %) (filter #(not-empty %) (string/split (read-line) #" ")))
        ; maximum number of turns before game over.
        _ (Integer/parseInt (read-line))
        [X0 Y0] (map #(Integer/parseInt %) (filter #(not-empty %) (string/split (read-line) #" ")))]
    (loop [current-position [X0 Y0]
           ; rectangle where the bomb is located
           search-area {:xmin 0 :xmax (dec W) :ymin 0 :ymax (dec H)}
           ; the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
           bomb-dir (read-line)]
      (let [new-area (shrink-area current-position search-area bomb-dir)
            new-position (centerpoint new-area)]
        (output (str (first new-position) " " (second new-position)))
        (recur new-position new-area (read-line))))))
