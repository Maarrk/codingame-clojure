(ns codingame.mime-type-test
  (:require [clojure.test :refer :all]
            [codingame.mime-type :refer :all]))

(deftest test-filename->extension
  (testing "Simple case"
    (let [filename "foo.txt"]
      (is (= "txt"
             (filename->extension filename)))))

  (testing "No extension"
    (let [filename "txt"]
      (is (= nil
             (filename->extension filename)))))

  (testing "Uppercase"
    (let [filename "BOOBA.JPG"]
      (is (= "jpg"
             (filename->extension filename)))))

  (testing "Trailing dot"
    (let [filename "linkinpark-numb.mp3."]
      (is (= nil
             (filename->extension filename))))))