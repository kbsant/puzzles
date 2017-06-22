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
;; [[a c 1] [a b 2] [c b 1] [a c 3] [b a 1] [b c 2] [a c 1] [a b 4] [c b 1] [c a 2] [b a 1] [c b 3] [a c 1] [a b 2] [c b 1]]
;; To test the output, each step is applied in a loop to the tower data.
;; The "move" function takes care of applying each step to move a disc from one tower to another.
;; When the loop is done, the completed actual data should match the expected data.
(defn test-towers [solver-fn]
  (let [a {:id :a :data [4 3 2 1]}
        b {:id :b :data []}
        c {:id :c :data []}
        start-towers {:a a :b b :c c}
        expected {:a (assoc a :data [])
                  :b (assoc b :data [ 4 3 2 1])
                  :c c}
        move (fn [towers src dst]
                (let [value (peek (get-in towers [src :data]))]
                  (-> towers
                    (update-in [src :data] pop)
                    (update-in [dst :data] conj value))))
        actual (loop [working start-towers
                      steps (solver-fn a b c)]
                  (if (empty? steps)
                    working
                    (let [[current-step & next-steps] steps
                          [src dst] current-step
                          next-working (move working src dst)]
                      #_(println (str "working: " working " steps: " steps))
                      (recur next-working next-steps))))]
    [actual expected]))

(deftest test-towers-rec
  (testing "the recursive, data vector-based implementation of Towers of Hanoi moves a pile of 4 discs from tower A to B via C"
    (let [[actual expected] (test-towers towers-rec)]
      (is (= actual expected)))))

(defn mkfn-towers-height
  "Adapter to make a function that only takes the height accept towers with data vectors provided by the test"
  [f]
  (let [height            (fn [a] (count (:data a)))]
    (fn [ a b c] (f (:id a) (:id b) (:id c) (height a)))))

(deftest test-towers-rec-height
  (testing "the recursive, height-based implementation of Towers of Hanoi moves a pile of 4 discs from tower A to B via C"
    (let [height            (fn [a] (count (:data a)))
          test-fn           (mkfn-towers-height towers-rec-height)
          [actual expected] (test-towers test-fn)]
      (is (= actual expected)))))

(deftest test-towers-stack-height
  (testing "the tail-recursive, height-based implementation of Towers of Hanoi moves a pile of 4 discs from tower A to B via C"
    (let [height            (fn [a] (count (:data a)))
          test-fn           (mkfn-towers-height towers-stack-height)
          [actual expected] (test-towers test-fn)]
      (is (= actual expected)))))
