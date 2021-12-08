#_{:clj-kondo/ignore [:refer-all]}
(ns codingame.shadows-of-the-knight-test
  (:require [clojure.test :refer :all]
            [codingame.shadows-of-the-knight :refer :all]))

((deftest test-shrink-area
   (testing "Puzzle explanation cases"
     (let [position [2 5]
           area {:xmin 0 :xmax 9 :ymin 0 :ymax 9}
           direction "UR"]
       (is (= {:xmin 3 :xmax 9 :ymin 0 :ymax 4}
              (shrink-area position area direction))))
     (let [position [5 4]
           area {:xmin 3 :xmax 9 :ymin 0 :ymax 4}
           direction "R"]
       (is (= {:xmin 6 :xmax 9 :ymin 4 :ymax 4}
              (shrink-area position area direction)))))))
