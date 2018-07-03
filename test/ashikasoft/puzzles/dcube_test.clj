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
  (testing "rtop- once steps once."
    (is (= [8 7 6
            1   5
            2 3 4]
           (-> initial-cube rtop- (subvec 0 8))))) 
  (testing "rtop- 7 times is the same as rtop+ once."
    (is (= (rtop+ initial-cube)
           (repeat-on-data initial-cube 7 rtop-)))) 
  (testing "rtop- 8 times goes back to the original."
    (is (= [7 6 5
            8   4
            1 2 3]
           (repeat-on-data initial-cube 8 rtop-)))))

(deftest test-rtop+
  (testing "rtop+ once steps once."
    (is (= [6 5 4
            7   3
            8 1 2]
           (-> initial-cube rtop+ (subvec 0 8))))) 
  (testing "rtop+ 7 times is the same as rtop- once."
    (is (= (rtop- initial-cube)
           (repeat-on-data initial-cube 7 rtop+)))) 
  (testing "rtop+ 8 times goes back to the original."
    (is (= [7 6 5
            8   4
            1 2 3]
           (repeat-on-data initial-cube 8 rtop+))))

  (testing "rtop+ 8 times goes back to the original."
    (is (= 1 nil)))) 

(deftest test-rbottom-
  (testing "rbottom- once steps once."
    (is (= 1 nil))) 
  (testing "rbottom- 8 times goes back to the original."
    (is (= 1 nil)))) 

(deftest test-rbottom+
  (testing "rbottom+ once steps once."
    (is (= 1 nil))) 
  (testing "rbottom+ 8 times goes back to the original."
    (is (= 1 nil)))) 


