(ns codingame.mime-type
  (:require [clojure.string :as string])
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn output [msg] (println msg) (flush))
(defn debug [msg] (binding [*out* *err*] (println msg) (flush)))

(defn lines->mapping
  [lines]
  (reduce (fn [m line]
            (let [words (string/split line #" ")
                  extension (string/lower-case (first words))
                  mime (second words)]
              (assoc m extension mime)))
          {} lines))

(defn filename->extension
  [filename]
  (if (= (last filename) \.)
    nil
    (let [parts (string/split filename #"\.")]
      (if (>= (count parts) 2)
        (string/lower-case (last parts))
        nil))))

(defn -main
  #_{:clj-kondo/ignore [:unused-binding]} [& args]
  (let [; Number of elements which make up the association table.
        N (Integer/parseInt (read-line))
        ; Number Q of file names to be analyzed.
        Q (Integer/parseInt (read-line))
        type-mapping (lines->mapping (repeatedly N read-line))]

    (dotimes [i Q]
      (let [; One file name per line.
            filename (read-line)
            extension (filename->extension filename)]
        (output (get type-mapping extension "UNKNOWN"))))))
