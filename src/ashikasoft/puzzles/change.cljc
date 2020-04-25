(ns ashikasoft.puzzles.change)

(defn total-amount [coins]
  (apply +
         (for [d (keys coins)]
           (* d (get coins d)))))

(defn coin-steps [steps coins]
  (let [[d _] (first coins)]
    (if d
      (recur (conj steps coins) (dissoc coins d))
      steps)))

(defn make-change
  "Given an amount, make change using coins.
  The solutions form a tree:
  ------- 10 [5 xs]
      ---  5 [2 xs]
      --- ...
      --- 0.1 []
  The ways to form change correspond to the leaves of the tree where the amount has been reduced to zero."
  ([amount coins-vec]
   (let [coins (into (sorted-map-by >) coins-vec)
         steps (coin-steps [] coins)
         change-fn #(make-change {} amount %)]
     (reduce into #{} (map change-fn steps))))
  ([trail amount coins] 
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
       (recur trail amount (dissoc coins d))
       ;; coin fits amount
       :else ;; expand combinations of next-coins, removing largest d for each
       (let [next-trail (update trail d (fnil inc 0))
             next-coins (update coins d (fnil dec 0))
             next-steps (coin-steps [] next-coins)
             change-fn #(make-change next-trail (- amount d) %)]
         (reduce into #{} (map change-fn next-steps))
         )))))

   #_
(comment
  "Repl session"
  (require '[ashikasoft.puzzles.change :as c])
  (c/make-change 9 [[5 5]])
  :=> #{}
  (c/make-change 9 [[5 5] [2 5] [1 10]])
  :=> #{
  {5 1, 2 2}
  {5 1, 2 1, 1 2}
  {5 1, 1 4}
  {2 4, 1 1}
  {2 3, 1 3}
  {2 2, 1 5}
  {2 1, 1 7}
  {1 9}
  })
