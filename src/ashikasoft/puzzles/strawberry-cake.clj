(ns ashikasoft.puzzles.strawberry-cake)

;; cake structure:
;; :top r0
;; :bottom rh
;; :left c0
;; :right cw
;; :strawberries #{ [r c] ...}

(defn bisect [{:keys [rows cols strawberries] :as cake}]
  (let [[s & srest] [strawberries]]
    :??? ))

(defn split-cakes [done [cake & rest :as cakes]]
  (cond
    ;; no cakes left to check
    (empty? cakes) done
    ;; cake has a single strawberry left, move it to the done set
    (= 1 (count (:strawberries cake))) (recur (conj done cake) rest)
    ;; multiple strawberries in the cake, need to split further
    :else (recur done (concat rest (bisect cake)))))
;; one approach is to paint the cake after solving the slices.
;; other approach is to paint it on the fly - more efficient, but a bit more complex


(defn paint-slice [w indices grid {:keys [top bottom left right strawberries]}]
  (let [index (get indices (first strawberries))
        cells (for [r (range top (inc bottom)), c (range left (inc right))]
                (get-offset w r c))]
    (reduce (fn [g offset] (assoc g offset index)) grid cells)))

(defn paint-cake [{:keys [bottom right strawberries] :as orig-cake} cakes]
  (let [[h w] [(inc bottom) (inc right)]
        indices (zipmap strawberries (range 1 (inc (count strawberries)))) 
        blank-grid (into [] (repeat 0 (* h w)))]
    (reduce (partial paint-slice w indices) blank-grid cakes)))

(defn grid->str [w grid]
  (->> grid (partition w) (interpose '(\newline)) (reduce into []) (apply str)))

;; in this version, rather than a list of cakes, 'done' is a blank grid.
;; actually, not much difference in performance, but added coupling/complexity
(defn split-cakes2 [indices done [cake & rest :as cakes]]
  (cond
    ;; no cakes left to check
    (empty? cakes) done
    ;; cake has a single strawberry left, move it to the done set
    (= 1 (count (:strawberries cake))) (recur (paint-slice indices done cake) rest)
    ;; multiple strawberries in the cake, need to split further
    :else (recur done (concat rest (bisect cake)))))
