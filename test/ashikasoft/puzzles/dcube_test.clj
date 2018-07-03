(ns ashikasoft.puzzles.dcube-test
  (:require [ashikasoft.puzzles.dcube :refer :all]
            [clojure.test :refer :all]))

(defn repeat-on-data
  "Call a function f on the given data, then call it again on the return value, a total of n times."
  [data n f]
  (reduce #(%2 %1) data (repeat n f)))

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
           (repeat-on-data initial-cube 3 rtop-)))) 
  (testing "rtop- 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rtop-)))))

(deftest test-rtop+
  (testing "rtop+ once turns a side once."
    (is (= [5 4 3
            6   2
            7 8 1]
           (-> initial-cube rtop+ (subvec 0 8))))) 
  (testing "rtop+ 3 times is the same as rtop- once."
    (is (= (rtop- initial-cube)
           (repeat-on-data initial-cube 3 rtop+)))) 
  (testing "rtop+ 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rtop+)))))

(deftest test-rbottom-
  (testing "rbottom- once steps once."
    (is (= 1 nil))) 
  (testing "rbottom- 3 times is the same as rbottom+ once."
    (is (= (rbottom+ initial-cube)
           (repeat-on-data initial-cube 3 rbottom-))) 
   (testing "rbottom- 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rbottom-))))))

(deftest test-rbottom+
  (testing "rbottom+ once steps once."
    (is (= 1 nil))) 
  (testing "rbottom+ 3 times is the same as rbottom- once."
    (is (= (rbottom- initial-cube)
           (repeat-on-data initial-cube 3 rbottom+))) 
   (testing "rbottom+ 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rbottom+))))))

(deftest test-rleft-
  (testing "rleft- once steps once."
    (is (= 1 nil))) 
  (testing "rleft- 3 times is the same as rleft+ once."
    (is (= (rleft+ initial-cube)
           (repeat-on-data initial-cube 3 rleft-))) 
   (testing "rleft- 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rleft-))))))

(deftest test-rleft+
  (testing "rleft+ once steps once."
    (is (= 1 nil))) 
  (testing "rleft+ 3 times is the same as rleft- once."
    (is (= (rleft- initial-cube)
           (repeat-on-data initial-cube 3 rleft+))) 
   (testing "rleft+ 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rleft+))))))

(deftest test-rright-
  (testing "rright- once steps once."
    (is (= 1 nil))) 
  (testing "rright- 3 times is the same as rright+ once."
    (is (= (rright+ initial-cube)
           (repeat-on-data initial-cube 3 rright-))) 
   (testing "rright- 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rright-))))))

(deftest test-rright+
  (testing "rright+ once steps once."
    (is (= 1 nil))) 
  (testing "rright+ 3 times is the same as rright- once."
    (is (= (rright- initial-cube)
           (repeat-on-data initial-cube 3 rright+))) 
   (testing "rright+ 4 times goes back to the original."
    (is (= initial-cube
           (repeat-on-data initial-cube 4 rright+))))))

