(ns codingame.the-labirynth ; (ns Player
  (:require [clojure.string :as string])
  (:gen-class))

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn backtracking
  ([end visited-from]
   (backtracking end visited-from (list end)))
  ([end visited-from path]
   (let [previous-node (get visited-from end)]
     (if (or (= previous-node nil) (< (first previous-node) 0))
       path
       (recur previous-node visited-from (cons previous-node path))))))

(defn neighbors
  "Neighbouring positions inside maze rectangle"
  [maze [pos-r pos-c]]
  (filter #(not= nil %)
          #_{:clj-kondo/ignore [:missing-else-branch]}
          [(if (< 0 pos-r) [(dec pos-r) pos-c])
           (if (< pos-r (:R maze)) [(inc pos-r) pos-c])
           (if (< 0 pos-c) [pos-r (dec pos-c)])
           (if (< pos-c (:C maze)) [pos-r (inc  pos-c)])]))

(defn get-cell
  [maze [pos-r pos-c]]
  (nth (nth (:rows maze) pos-r) pos-c))

(defn in?
  "true if coll contains x"
  [coll x]
  (some #(= x %) coll))

(defn bfs
  ([maze add-cells target-cells start]
   (let [start-seq (list start)]
     (bfs maze add-cells target-cells start-seq (reduce (fn [m k] (assoc m k [-1 -1])) {} start-seq))))
  ([maze add-cells target-cells queue visited-from]
   (if (empty? queue)
     nil
     (let [current-pos (first queue)
           current-cell (get-cell maze current-pos)
           unvisited-neighbors (filter #(= (get visited-from %) nil)
                                       (filter #(in? (concat add-cells target-cells) (get-cell maze %))
                                               (neighbors maze current-pos)))]
       (if (in? target-cells current-cell)
         (backtracking current-pos visited-from)
         (recur maze add-cells target-cells
                (concat (rest queue) unvisited-neighbors)
                (reduce (fn [m n] (assoc m n current-pos)) visited-from unvisited-neighbors)))))))

(defn first-move
  [path]
  (let [[r1 c1] (first path)
        [r2 c2] (second path)]
    (case [(- r2 r1) (- c2 c1)]
      [-1 0] "UP"
      [0 -1] "LEFT"
      [0  1] "RIGHT"
      [1  0] "DOWN")))

(defn cell-position
  "Returns the position of first cell of the specified kind or nil"
  [maze cell]
  (->> maze
       :rows
       (map-indexed (fn [i row] [i (string/index-of row cell)]))
       (filter #(not= nil (second %)))
       first))

(defn get-path
  [maze pos task]
  (if (nil? pos)
    nil
    (case task
      :explore (bfs maze [\. \T] [\?] pos)
      :switch-control (bfs maze [\. \T] [\C] pos)
      :escape (bfs maze [\.] [\T] pos))))

(defn -main
  #_{:clj-kondo/ignore [:unused-binding]} [& args]
  (let [; R: number of rows.
        ; C: number of columns.
        ; A: number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
        [R C A] (map #(Integer/parseInt %) (filter #(not-empty %) (string/split (read-line) #" ")))]
    (loop [task :explore]
      (let [pos (map #(Integer/parseInt %) (filter #(not-empty %) (string/split (read-line) #" ")))
            rows (doall (repeatedly R read-line))
            maze {:R R, :C C, :rows rows}
            ; If standing on control room for whatever reason, time to escape
            task (if (= (get-cell maze pos) \C) :escape task)
            path (get-path maze pos task)]

        (debug task)
        (debug path)

        (if (and (= task :explore)
                 (or
                  (nil? path)  ; Could not find a cell to explore
                  ; There is an escape path short enough
                  (let [escape-path (get-path maze (cell-position maze \C) :escape)]
                    (and (not= nil escape-path) (<= (count escape-path) (inc A))))))
          (do
            (output (first-move (get-path maze pos :switch-control)))
            (recur :switch-control))
          (do
            (output (first-move path))
            (recur task)))))))