(ns ashikasoft.puzzles.strawberry-cake)

;; cake structure:
;; :rows [0 to H - 1]
;; :cols [0 to W - 1]
;; :strawberries [{:row r, :col c, :id 1 to K}]

(defn count-strawberries [cake]
  (count (:strawberries cake)))

(defn cake-contains [{:keys [rows cols]} r c]
  (let [c-first (first cols)
        c-last (peek cols)
        r-first (first rows)
        r-last (peek rows)]
    (and
     (< c-first c c-last)
     (< r-first r r-last))))

(defn partial-split [{:keys [rows cols strawberries] :as cake}]
  (let [[s & srest] [strawberries]]
    :??? ))

(defn split-cakes [done [cake & rest :as cakes]]
  (cond
    ;; no cakes left to check
    (empty? cakes)
    done
    ;; cake has a single strawberry left, move it to the done set
    (= 1 (count-strawberries cake))
    (recur (conj done cake) rest)
    ;; multiple strawberries in the cake, need to split further
    :else
    (recur done (concat rest (partial-split cake)))))
