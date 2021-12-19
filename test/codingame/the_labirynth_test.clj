(ns codingame.the-labirynth-test
  #_{:clj-kondo/ignore [:refer-all]}
  (:require [clojure.test :refer :all]
            [codingame.the-labirynth :refer :all]))

(def example-maze
  {:R 10, :C 30, :rows
   ["??????????????????????????????"
    "#..............???????????????"
    "#.#############???????????????"
    "#.....T........???????????????"
    "#.......................#.#..#"
    "#.#######################.#..#"
    "#.....##......##......#....###"
    "#...####..##..##..##..#..#...#"
    "#.........##......##.....#.C.#"
    "##############################"]})

(deftest test-get-cell
  (testing "Unexplored"
    (let [pos [0 0]]
      (is (= \?
             (get-cell example-maze pos)))))
  (testing "Wall"
    (let [pos [2 0]]
      (is (= \#
             (get-cell example-maze pos)))))
  (testing "Empty"
    (let [pos [2 1]]
      (is (= \.
             (get-cell example-maze pos))))))

(deftest test-bfs
  (testing "Exploring"
    (let [add-cells [\. \T]
          target-cells [\?]]
      (let [start [1 2]]
        (is (= [[1 2] [0 2]]
               (bfs example-maze add-cells target-cells start))))
      (let [start [3 12]]
        (is (= [[3 12] [3 13] [3 14] [3 15]]
               (bfs example-maze add-cells target-cells start)))))))

(deftest test-cell-position
  (testing "Starting position"
    (let [cell \T]
      (is (= [3 6]
             (cell-position example-maze cell)))))
  (testing "Control room"
    (let [cell \C]
      (is (= [8 27]
             (cell-position example-maze cell)))))
  (testing "Not present"
    (let [cell \X]
      (is (= nil
             (cell-position example-maze cell))))))