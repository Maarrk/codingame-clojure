#_{:clj-kondo/ignore [:refer-all]}
(ns codingame.defibrillators-test
  (:require [clojure.test :refer :all]
            [codingame.defibrillators :refer :all]))

(def example-text "3,879483
43,608177
3
1;Maison de la Prevention Sante;6 rue Maguelone 340000 Montpellier;;3,87952263361082;43,6071285339217
2;Hotel de Ville;1 place Georges Freche 34267 Montpellier;;3,89652239197876;43,5987299452849
3;Zoo de Lunaret;50 avenue Agropolis 34090 Mtp;;3,87388031141133;43,6395872778854")

(deftest test-line->defib
  (testing "Example data"
    (let [text "1;Maison;6 rue Maguelone;;3,8;43,6"]
      (is (= {:name "Maison" :lon 3.8 :lat 43.6}
             (line->defib text))))))
