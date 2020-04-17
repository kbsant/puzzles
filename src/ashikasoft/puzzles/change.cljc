(ns ashikasoft.puzzles.change)

(defn make-change
  "Given an amount, make change using coins.
  The solutions form a tree:
  ------- 10 [5 xs]
      ---  5 [2 xs]
      --- ...
      --- 0.1 []
  The ways to form change correspond to the leaves of the tree where the amount has been reduced to zero."
  ([amount coins]
   (let [guarded-coins (assoc coins 0 1)]
     (make-change {} #{} amount guarded-coins)))
  ([trail solutions amount coins [d & rest :as ds]]
   (let [[d & ds] (sort )])
   (cond
     ;; no amount left
     (zero? amount)
     #{trail} 
     ;; no coins left
     (not d)
     #{}
     ;; denomination is too large. try the next.
     (< amount d)
     (recur trail solutions amount (dissoc coins d) ds)
     ;; coin fits amount
     (>= amount coin)
     (let [next-coins (update coins d (fnil dec 0))])
     (recur
      
      ways
      (- amount coin)
      coins)
     ;; amount is too small for coin -- FIXME shuffle coins and combine/reduce solutions
     :else
     (conj ways (assoc way :error {:amount amount :coin coin}))
     
     )

   #_))
(comment
  "Repl session"
  (require '[ashikasoft.puzzles.change :as c])
  (c/make-change 7 [5])
  ;=> #{{5 1, :unchanged 2}}
  (c/make-change 7 [5 1])
  ;=> #{{5 1, 1 2}}
         )
