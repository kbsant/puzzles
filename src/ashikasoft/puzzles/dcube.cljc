(ns ashikasoft.puzzles.dcube)

;; Cube centers
;;
;;               blue   (top)
;; orange (left) white  (front) red (right)
;;               green  (bottom)
;;               yellow (back)
;;
;; Sorted cube
;;
;; faces: 6
;; cells: 9 per face
;; but only 20 moving parts.
;;
;;
;;          7  6  5
;;          8  B  4
;;          1  2  3
;;    19 N  9  W 10  R  20
;;         17 16 15
;;         18  G 14
;;         11 12 13
;;             Y
;;
;; [0 1 2 3 4 5 6 7  8 9 10 11 12 13 14 15 16 17 18 19]
;;  1 2 3 4 5 6 7 8 19 9 10 20 11 12 13 14 15 16 17 18
;;
;; 8 Operations -- rotate: 
;; * top (-, +)
;; * bottom (-, +)
;; * left (-, +)
;; * right (-, +)
;;
;; Implementation:

(def initial-cube
  "Initial cube data - sorted position."
  [         7  6  5
            8     4
            1  2  3
      19    9    10     20
           17 16 15
           18    14
           11 12 13])


;; indexes: 0 1 2 3 4 5 6 7
;; values:  7 6 5 8 4 1 2 3  ->  1 8 7 2 6 3 4 5
(defn rtop-
  "Rotate the top row to the left."
  [cube]
  (reduce
    into
    [[]
     (map #(get cube %) [5 3 0 6 1 7 4 2])
     (subvec cube 8)]))

;; indexes: 0 1 2 3 4 5 6 7
;; values:  7 6 5 8 4 1 2 3  ->  5 4 3 6 2 7 8 1
(defn rtop+
  "Rotate the top row to the right."
  [cube]
  (reduce
    into
    [[]
     (map #(get cube %) [2 4 7 1 6 0 3 5])
     (subvec cube 8)]))

;; indexes: 12 13 14 15 16 17 18 19
;; values:  17 16 15 18 14 11 12 13 -> 11 18 17 12 16 13 14 15
(defn rbottom-
  "Rotate the bottom row to the left."
  [cube]
  (into
    (subvec cube 0 12)
    (map #(get cube %) [17 15 12 18 13 19 16 14])))

;; indexes: 12 13 14 15 16 17 18 19
;; values:  17 16 15 18 14 11 12 13 -> 15 14 13 16 12 17 18 11
(defn rbottom+
  "Rotate the bottom row to the right."
  [cube]
  (into
    (subvec cube 0 12)
    (map #(get cube %) [14 16 19 13 18 12 15 17])))


;; indexes: 0  3  5  8  9 12 15 17 
;; values:  7  8  1 19  9 17 18 11 -> 1 9 17 8 18 11 19 7
(defn rleft-
  "Rotate the left column upward."
  [cube]
  (into [] (map #(get cube %) [5 1 2 9 4 12 6 7 3 15 10 11 17 13 14 8 16 0 18 19])))

;; indexes: 0  3  5  8  9 12 15 17 
;; values:  7  8  1 19  9 17 18 11 -> 11 19 7 18 8 1 9 17
(defn rleft+
  "Rotate the left column downward."
  [cube]
  (into [] (map #(get cube %) [17 1 2 8 4 0 6 7 15 3 10 11 5 13 14 9 16 12 18 19])))

;; indexes: 2 4 7 10 11 14 16 19
;; values:  5 4 3 10 20 15 14 13 -> 3 10 15 14 4 13 20 5
(defn rright-
  "Rotate the right column upward."
  [cube]
  (into [] (map #(get cube %) [0 1 7 3 10 5 6 14 8 9 16 4 12 13 19 15 11 17 18 2])))

;; indexes: 2 4 7 10 11 14 16 19
;; values:  5 4 3 10 20 15 14 13 -> 13 20 5 4 14 3 10 15
(defn rright+
  "Rotate the right column downward."
  [cube]
  (into [] (map #(get cube %) [0 1 19 3 11 5 6 2 8 9 4 16 12 13 7 15 10 17 18 14])))

;; TODO keep track of both the commands and the results
(defn next-steps [step previous]
  #{step})

