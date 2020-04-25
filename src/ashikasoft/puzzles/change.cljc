(ns ashikasoft.puzzles.change)

(defn total-amount [coins]
  (apply +
         (for [d (keys coins)]
           (* d (get coins d)))))

(defn make-change
  "Given an amount, make change using coins.
  The solutions form a tree:
  ------- 10 [5 xs]
      ---  5 [2 xs]
      --- ...
      --- 0.1 []
  The ways to form change correspond to the leaves of the tree where the amount has been reduced to zero."
  ([amount coins-vec]
   (make-change {} #{} amount (into (sorted-map-by >) coins-vec)))
  ([trail solutions amount coins] 
   (let [[d & ds] (keys coins)]
     (cond
       ;; no amount left
       (zero? amount)
       #{trail} 
       ;; no coins left, or not enough change
       (or (not d) (> amount (total-amount coins)))
       #{}
       ;; denomination is too large. try the next.
       (< amount d)
       (recur trail solutions amount (dissoc coins d))
       ;; coin fits amount
       :else ;; TODO -- expand combinations of next-coins, removing largest d for each
       (let [next-trail (update trail d (fnil inc 0))
             next-coins (update coins d (fnil dec 0))]
         (into #{} (make-change
                          next-trail
                          solutions
                          (- amount d)
                          next-coins)))
       
       ))))

   #_
(comment
  "Repl session"
  (require '[ashikasoft.puzzles.change :as c])
  (c/make-change 7 [[5 1]])
  ;=> #{{5 1, :unchanged 2}}
  (c/make-change 7 [[5 1] [1 2]])
  ;=> #{{5 1, 1 2}}
         )
