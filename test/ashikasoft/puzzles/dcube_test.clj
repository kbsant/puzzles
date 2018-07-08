(ns ashikasoft.puzzles.dcube-test
  (:require [ashikasoft.puzzles.dcube :refer :all]
            [clojure.test :refer :all]))

(defn- reduce-on-data
  [data fs]
  "Thread the given data through a sequence of functions."
  (reduce #(%2 %1) data fs))

(deftest test-initial-cube
  (testing "initial position of top row"
    (is (= [7 6 5
            8   4
            1 2 3]
           (subvec initial-cube 0 8))))
  (testing "initial position of bottom row"
    (is (= [17 16 15
            18    14
            11 12 13]
           (subvec initial-cube 12)))))

(deftest test-rtop-
  (testing "rtop- once turns a side once."
    (is (= [1 8 7
            2   6
            3 4 5]
           (-> initial-cube rtop- (subvec 0 8))))) 
  (testing "rtop- 3 times is the same as rtop+ once."
    (is (= (rtop+ initial-cube)
           (reduce-on-data initial-cube (repeat 3 rtop-))))) 
  (testing "rtop- 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rtop-))))))

(deftest test-rtop+
  (testing "rtop+ once turns a side once."
    (is (= [5 4 3
            6   2
            7 8 1]
           (-> initial-cube rtop+ (subvec 0 8))))) 
  (testing "rtop+ 3 times is the same as rtop- once."
    (is (= (rtop- initial-cube)
           (reduce-on-data initial-cube (repeat 3 rtop+))))) 
  (testing "rtop+ 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rtop+))))))

(deftest test-rbottom-
  (testing "rbottom- once turns a side once."
    (is (= [11 18 17
            12    16
            13 14 15]
           (-> initial-cube rbottom- (subvec 12)))))
  (testing "rbottom- 3 times is the same as rbottom+ once."
    (is (= (rbottom+ initial-cube)
           (reduce-on-data initial-cube (repeat 3 rbottom-)))) 
   (testing "rbottom- 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rbottom-)))))))

(deftest test-rbottom+
  (testing "rbottom+ once turns a side once."
    (is (= [15 14 13
            16    12
            17 18 11]
           (-> initial-cube rbottom+ (subvec 12)))))
  (testing "rbottom+ 3 times is the same as rbottom- once."
    (is (= (rbottom- initial-cube)
           (reduce-on-data initial-cube (repeat 3 rbottom+)))) 
   (testing "rbottom+ 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rbottom+)))))))

(deftest test-rleft-
  (testing "rleft- once steps once."
    (is (= [1 6 5 9 4 17 2 3 8 18 10 20 11 16 15 19 14 7 12 13]
           (rleft- initial-cube)))) 
  (testing "rleft- 3 times is the same as rleft+ once."
    (is (= (rleft+ initial-cube)
           (reduce-on-data initial-cube (repeat 3 rleft-)))) 
   (testing "rleft- 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rleft-)))))))

(deftest test-rleft+
  (testing "rleft+ once steps once."
    (is (= [11 6 5 19 4 7 2 3 18 8 10 20 1 16 15 9 14 17 12 13]
           (rleft+ initial-cube)))) 
  (testing "rleft+ 3 times is the same as rleft- once."
    (is (= (rleft- initial-cube)
           (reduce-on-data initial-cube (repeat 3 rleft+)))) 
   (testing "rleft+ 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rleft+)))))))

(deftest test-rright-
  (testing "rright- once steps once."
    (is (= [7 6 3 8 10 1 2 15 19 9 14 4 17 16 13 18 20 11 12 5]
           (rright- initial-cube)))) 
  (testing "rright- 3 times is the same as rright+ once."
    (is (= (rright+ initial-cube)
           (reduce-on-data initial-cube (repeat 3 rright-)))) 
   (testing "rright- 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rright-)))))))

(deftest test-rright+
  (testing "rright+ once steps once."
    (is (= [7 6 13 8 20 1 2 5 19 9 4 14 17 16 3 18 10 11 12 15]
           (rright+ initial-cube)))) 
  (testing "rright+ 3 times is the same as rright- once."
    (is (= (rright- initial-cube)
           (reduce-on-data initial-cube (repeat 3 rright+)))) 
   (testing "rright+ 4 times goes back to the original."
    (is (= initial-cube
           (reduce-on-data initial-cube (repeat 4 rright+)))))))

(deftest test-ops-complements
  (testing "there are 8 operations."
    (is (= 8 (count ops))))
  (testing "testing operations and their complements."
    (let [complements (map ops-complements ops)
          original-data (repeat 8 initial-cube)
          transformed-data (map #(%1 initial-cube) ops)
          reverted-data (map (fn [[k v]](k v)) (zipmap complements transformed-data))]
      (testing "every operation results in a value different from the original."
        (is (not= original-data transformed-data))) 
      (testing "every complement applied after each operation should revert to the original."
        (is (= original-data reverted-data))))))

(deftest test-next-steps
  (testing "step should generate all 8 next available steps when previous is empty."
    (is (= 8 (count (next-steps initial-cube [])))))
  (testing "the set of next steps should be the operators."
    (is (= (into #{} ops) (into #{} (map (comp peek second) (next-steps initial-cube []))))))
  (testing "the set of next states should be the result of the operators on the previous state."
    (is (= (into #{} (map #(@% initial-cube) ops))
           (into #{} (map first (next-steps initial-cube [])))))))

(deftest test-filter-previous
  (let [results [[[0] '()], [[1 2 3] '(a b c)], [[2 3 4] '(d e f)], [[9] '(z)]]]
    (testing "Filtering with an empty history passes everything."
      (is (= results (filter-previous {} results)))) 
    (testing "Previously encountered states are filtered out."
      (is (= [[[0] '()], [[1 2 3] '(a b c)],[[9] '(z)]]
             (filter-previous #{[2 3 4]} results))))))

(deftest test-solve
  (testing "Solving the initial state should be empty."
    (is (empty? (:steps (solve #{initial-cube})))))
  (testing "It takes 1 top rotation to reach this target."
    (is (= 'rtop- (meta-name (peek (:steps (solve #{(rtop- initial-cube)})))))))
  (testing "A short sequence of operations is solvable."
    (is (= '(rtop- rleft+ rbottom+ rright-)
           (map meta-name (:steps (solve #{((comp rtop- rleft+ rbottom+ rright-) initial-cube)})))))))

(def test-reverse-solve
  (testing "Applying the reversed complement of the solution gives the initial cube."
    (let [target ((comp rtop- rleft+ rbottom+ rright-) initial-cube)
          steps (:steps (solve #{target}))
          reverse-steps (map ops-complements (reverse steps))]
      (is (= initial-cube ((apply comp reverse-steps) target)))))
 #_(testing "Sample target"
    (let [target [5 18 7 8 19 1 2 3 6 9 10 12 17 16 15 14 4 11 20 13]
          #_ #_ target2 (solve 1000000 #{[5 18 7 8 19 1 2 3 6 9 10 12 17 16 15 14 4 11 20 13]})
          steps (solve #{target})
          reverse-steps (map ops-complements (reverse steps))]
      (is (= initial-cube ((apply comp reverse-steps) target))))))

