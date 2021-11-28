#_{:clj-kondo/ignore [:refer-all]}
(ns codingame.death-first-search-test
  (:require [clojure.test :refer :all]
            [codingame.death-first-search :refer :all]))

(deftest test-create-graph
  (testing "Simplest example"
    (let [links [[0 1] [1 2]]]
      (is (= {0 #{1}, 1 #{0 2}, 2 #{1}}
             ;; Function can return the keys in random order
             (into (sorted-map) (create-graph links))))))

  (testing "Different order"
    (let [links [[1 2] [0 1]]]
      (is (= {0 #{1}, 1 #{0 2}, 2 #{1}}
             (into (sorted-map) (create-graph links))))))

  (testing "Duplicate links"
    (let [links [[0 1] [1 2] [1 0]]]
      (is (= {0 #{1}, 1 #{0 2}, 2 #{1}}
             (into (sorted-map) (create-graph links)))))))

(deftest test-bfs
  (let [graph {0 #{1}, 1 #{0 2}, 2 #{1}}]
    (testing "Neighbour"
      (let [start 0
            pred #(= % 1)]
        (is (= [0 1]
               (bfs graph pred start)))))
    (testing "Second node"
      (let [start 0
            pred #(= % 2)]
        (is (= [0 1 2]
               (bfs graph pred start))))))
  (let [graph {0 #{1}, 1 #{0 2}, 2 #{1 3}, 3 #{2}}]
    (testing "Multiple starting nodes"
      (let [start [0 3]
            pred #(= % 2)]
        (is (= [3 2]
               (bfs graph pred start)))))))

(deftest test-backtracking
  (testing "Simple path"
    (let [end 2
          visited-from {1 0, 2 1}]
      (is (= [0 1 2]
             (backtracking end visited-from))))))

(deftest test-remove-link
  (testing "Simple case"
    (let [graph {0 #{1}, 1 #{0 2}, 2 #{1}}
          link [2 1]]
      (is (= {0 #{1}, 1 #{0}, 2 #{}}
             (into (sorted-map) (remove-link graph link)))))))

(deftest test-read-links!
  (testing "Two line input"
    (let [count 2
          line "0 1"]
      (with-redefs [repeatedly repeat
                    read-line line]
        (is (= [[0 1] [0 1]]
               (read-links! count)))))))