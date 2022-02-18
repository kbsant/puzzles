(ns anketeur.puzzles.coins-test
  (:require
   [anketeur.puzzles.coins :as coins]
   [clojure.test :refer [deftest is testing]]))

(deftest total-amount-test
  (testing "Given a map of coins to their count, compute the total amount."
    (let [coins {5 2, 2 2, 1 3}]
      (is (= 17
             (coins/total-amount coins))))))

(deftest coin-steps-test
  (testing "Given a sorted map of coins, return a list of steps with the largest denomination removed in succession."
    (let [coins (into (sorted-map-by >)  [[1 3] [5 2] [2 2]])]
      (is (= [{5 2, 2 2, 1 3} {2 2, 1 3} {1 3}]
             (coins/coin-steps coins))))))

(deftest make-change-test
  (testing "Given an amount and big coins, show there is no way to make change."
    (let [amount 9
          coins [[5 5]]]
      (is (= #{}
             (coins/make-change amount coins)))))
  (testing "Given an amount and some coins, show all the ways to make the amount using the coins."
    (let [amount 9
          coins [[5 5] [2 5] [1 10]]]
      (is (= #{{5 1, 2 2}            
               {2 4, 1 1}
               {2 1, 1 7}
               {1 9}
               {5 1, 2 1, 1 2}
               {5 1, 1 4}
               {2 3, 1 3}
               {2 2, 1 5}}
             (coins/make-change amount coins))))))
