(ns anketeur.puzzles.mergesort)

(declare merge)
; mergesort implementation: keep splitting input into 2, then merge outputs.
(defn mergesort "experimental merge sort implementation" [v]
   (println "Sorting input: " v)
      (let [len (count v) ]
         (if (<= len 1) v
             (let [mid (/ len 2)
                   left (subvec v 0 mid)
                   right (subvec v mid)]
                   (merge [] (mergesort left) (mergesort right))))))

; merge 2 sorted lists by taking elements of each list and  accumulating the smaller one into the output 
(defn- merge [acc left right]
    (println "Merging input: " left " , " right)
      (cond (empty? left) (into acc right)
            (empty? right) (into acc left)
            :else
            (let [[l & ll] left
                 [r & rr] right
                 [next-smallest next-left next-right]  (if (<= l r) [l ll right] [r left rr])
                  next-acc (conj acc next-smallest)]
                  (recur next-acc next-left next-right) )))


; Try it out on the following vector
(comment
  (println "Result: " (mergesort [3 5 1 2 9 5 7 4])))

