(ns ashikasoft.puzzles.towers-test
  (:require [clojure.test :refer :all]
            [ashikasoft.puzzles.towers :refer :all]))

;; test-towers
;; This test takes a functio that solves the of Towers of Hanoi, runs it on
;; sample data, then compares the actual and expected solutions.
;; Tower function input:
;; The input data corresponds to [a, b, and c] in the "let" statement of test-towers.
;; Each tower is a map with an :id and data.
;; Tower function output:
;; The output is a vector of steps:
;; [[1 a c] [2 a b] [1 c b] [3 a c] [1 b a] [2 b c] [1 a c] [4 a b] [1 c b] [2 c a] [1 b a] [3 c b] [1 a c] [2 a b] [1 c b]]
;; To test the output, each step is applied in a loop to the tower data.
;; The "move" function takes care of applying each step to move a disc from one tower to another.
;; When the loop is done, the completed actual data should match the expected data.
(defn test-towers [towers-fn]
  (let [a {:id :a :data [4 3 2 1]}
        b {:id :b :data []}
        c {:id :c :data []}
        start-set {:a a :b b :c c}
        expected {:a (assoc a :data [])
                  :b (assoc b :data [ 4 3 2 1])
                  :c c}
        move (fn [set [value from to]]
                (when (= value (peek (get-in set [from :data])))
                  (-> set
                    (update-in [from :data] pop)
                    (update-in [to :data] conj value))))
        actual (loop [working start-set
                      steps (towers-fn a b c)]
                  (let [[current & next-steps] steps
                        next-working (when current (move working current))]
                    #_(println (str "working: " working " steps: " steps))
                    (if next-working
                      (recur next-working next-steps)
                      working)))]
    [actual expected]))

(deftest test-towers-rec
  (testing "the recursive implementation of Towers of Hanoi moves a pile of 4 discs from tower A to B via C"
    (let [[actual expected] (test-towers towers-rec)]
      (is (= actual expected)))))
