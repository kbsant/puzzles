(ns ashikasoft.puzzles.change)

(defn make-change
  "Given an amount, make change using coins"
  ([amount coins]
   (make-change {} amount coins))
  ([tab amount [coin & rest :as coins]]
   (cond
     ;; no amount left
     (zero? amount)
     #{tab}
     ;; no coins left
     (not coin)
     #{(assoc tab :unchanged amount)}
     ;; coin fits amount
     (>= amount coin)
     (recur
      (update tab coin (fnil inc 0))
      (- amount coin)
      coins)
     ;; amount is too small for coin -- FIXME shuffle coins and combine/reduce solutions
     (< amount coin)
     (recur tab amount rest)
     :else
     (assoc tab :error {:amount amount :coin coin})
     
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
