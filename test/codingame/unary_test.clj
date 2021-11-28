(ns codingame.unary-test
  (:require [clojure.test :refer :all]
            [codingame.unary :refer :all]))

(deftest test-text->binary
  (testing "Example from description"
    (let [text "CC"]
      (is (= "10000111000011"
             (text->binary text)))))
  (testing "Leading zeros"
    (let [text "%"]
      (is (= "0100101"
             (text->binary text))))))

(deftest test-repetitions
  (testing "Letter repetitions"
    (let [text "abbccc"]
      (is (= [[\a 1] [\b 2] [\c 3]]
             (repetitions text))))))

(deftest test-encode
  (testing "Encoding from description"
    (let [repeats [[\1 1] [\0 4] [\1 2]]]
      (is (= "0 0 00 0000 0 00"
             (encode repeats))))))
