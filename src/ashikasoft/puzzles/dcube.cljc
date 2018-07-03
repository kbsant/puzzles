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


(defn rtop-
  "Rotate the top row to the left."
  [cube]
  cube)
;; cube [0 ~ 7] := cube [1 ~ 7], cube [0]

(defn rtop+
  "Rotate the top row to the right."
  [cube]
  cube)
;; cube [0 ~ 7] := cube [7] , cube [1 ~ 6]

(defn rbottom-
  "Rotate the bottom row to the left."
  [cube]
  cube)
;; cube [12 ~ 19] := cube [13 ~ 19], cube [12]

(defn rbottom+
  "Rotate the bottom row to the right."
  [cube]
  cube)
;; cube [12 ~ 19] := cube [19] , cube [12 ~ 18]


