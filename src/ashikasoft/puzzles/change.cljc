(ns ashikasoft.puzzles.change)

(defn make-change
  "Given an amount, make change using coins.
  The solutions form a tree:
  ------- 10 [5 xs]
      ---  5 [2 xs]
      --- ...
      --- 0.1 []
  The ways to form change correspond to the leaves of the tree."
  ([amount coins]
   (make-change {} #{} amount coins))
  ([parent amount [coin & rest :as coins]]
   (cond
     ;; no amount left
     (zero? amount)
     parent
     ;; no coins left
     (not coin)
     nil
     (< amount coin)
     (recur way ways amount rest)
     ;; coin fits amount
     (>= amount coin)
     (recur
      (update way coin (fnil inc 0))
      ways
      (- amount coin)
      coins)
     ;; amount is too small for coin -- FIXME shuffle coins and combine/reduce solutions
     :else
     (conj ways (assoc way :error {:amount amount :coin coin}))
     
     )))

#_
(comment
  "Repl session"
  (require '[ashikasoft.puzzles.change :as c])
  (c/make-change 7 [5])
  ;=> #{{5 1, :unchanged 2}}
  (c/make-change 7 [5 1])
  ;=> #{{5 1, 1 2}}
         )
