(ns puzzles.towers-test
  (:require [clojure.test :refer :all]
            [puzzles.towers :refer :all]))


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
      (is (= actual expected))))

(defn test-towers-rec [] (test-towers towers-rec))
