(ns codingame.death-first-search
  (:require [clojure.string :as string])
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn create-graph
  "Change a vector of connections (edge list) into adjacency list"
  [links]
  (apply merge-with into (map (fn
                                [[n1 n2]]
                                {n1 #{n2}, n2 #{n1}}) links)))

(defn read-links!
  [count]
  (map (fn [line]
         (map (fn [word]
                (Integer/parseInt word))
              (string/split line #" ")))
       (repeatedly count read-line)))

(defn backtracking
  ([end visited-from]
   (backtracking end visited-from (list end)))
  ([end visited-from path]
   (let [previous-node (get visited-from end)]
     (if (or (= previous-node nil) (< previous-node 0))
       path
       (recur previous-node visited-from (cons previous-node path))))))

(defn bfs
  "Breadth first search, returning path to node matching predicate"
  ([graph pred start]
   (let [start-seq (if (seqable? start) start (list start))]
     (bfs graph pred start-seq (reduce (fn [m k] (assoc m k -1)) {} start-seq))))
  ([graph pred queue visited-from]
   (if (empty? queue)
     nil
     (let [current (first queue)
           unvisited-neighbors (filter #(= (get visited-from %) nil) (get graph current))]
       (if (pred current)
         (backtracking current visited-from)
         (recur graph pred
                (concat (rest queue) unvisited-neighbors)
                (reduce (fn [m n] (assoc m n current)) visited-from unvisited-neighbors)))))))

(defn remove-link
  "Remove connection between n1 and n2 from both sets"
  [graph [n1 n2]]
  (assoc
   (assoc graph
          n1 (disj (get graph n1) n2))
   n2 (disj (get graph n2) n1)))

;; FIXME: There must be a better way
(def links-atom (atom []))
(def exits-atom (atom []))

(defn -main
  #_{:clj-kondo/ignore [:unused-binding]} [& args]
  (let [; N: the total number of nodes in the level, including the gateways
        ; L: the number of links
        ; E: the number of exit gateways
        [N L E] (map #(Integer/parseInt %) (filter #(not-empty %) (string/split (read-line) #" ")))
        ;; graph (create-graph (read-links! L))
        ;; exits (repeatedly E (Integer/parseInt (read-line)))
        ]
    (dotimes [i L]
      (let [; N1: N1 and N2 defines a link between these nodes
            [N1 N2] (map #(Integer/parseInt %) (filter #(not-empty %) (string/split (read-line) #" ")))]
        (reset! links-atom (conj @links-atom [N1 N2]))))
    (dotimes [i E]
      (let [; the index of a gateway node
            EI (Integer/parseInt (read-line))]
        (reset! exits-atom (conj @exits-atom EI))))
    (let [links @links-atom
          exits @exits-atom
          graph (create-graph links)]
      (loop [SI (Integer/parseInt (read-line))
             updated-graph graph]
        (let [path (bfs updated-graph #(= SI %) exits)
              link (take 2 path)]
          (debug path)
          (output (string/join " " (map str link)))
          (recur (Integer/parseInt (read-line))
                 (remove-link updated-graph link)))))
    ;; (while true
    ;;   (let [; The index of the node on which the Bobnet agent is positioned this turn
    ;;         SI (Integer/parseInt (read-line))]
    ;;     ; Example: 0 1 are the indices of the nodes you wish to sever the link between
    ;;     (output "0 1")))
    ))
