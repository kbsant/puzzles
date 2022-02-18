(ns anketeur.puzzles.csudoku)

; sudoku board geometry
; the side of a table, e.g. 9 (9x9 table)
(def table-side 9)

; the side of a block, e.g. 3 (3x3 block containing cells numbered 1-9)
(def block-side (Math/sqrt table-side))

; possible values of a cell, e.g. 1 to 9
(def possible-values
  (range 1 (inc table-side)))

; number of cells in a board, e.g. 81
(def board-size (* table-side table-side))

; an empty board
(def empty-board (vec (take board-size (repeat 0))))

; checks whether a board is fully filled.
; a board is complete if it doesn't have any zeroes
(defn is-complete-board? [b]
  (= board-size
     (count (filter #(not= 0 %) b))))

; row col to index
(defn rc-to-index [row col]
  (+ (dec col) (* table-side (dec row))))

; index to row col
(defn index-to-rc [i]
  [(inc (quot i table-side)) (inc (rem i table-side))])

; checks whether a row in a board contains a value
(defn row-contains? [board row v]
  (let [start (* table-side (dec row))
        end (+ start table-side)
        row-cells (subvec board start end)]
    (some #{v} row-cells)))

; checks whether a column in board contains a value
(defn col-contains? [board col v]
  (let [col-keys (->> (range table-side) (map #(+ (dec col) (* table-side %))))
        col-cells (map board col-keys)]
    (some #{v} col-cells)))

; checks whether a block in a board contains a value
; the board can be found using the row and col
; for a 9x9 board, a block is a 3x3 region within that board.
(defn block-contains? [board row col v]
  (let [block-min (fn [n] (int (inc (* block-side (quot (dec n) block-side)))))
        block-max (fn [n] (+ (block-min n) block-side))
        range-row (range (block-min row) (block-max row))
        range-col (range (block-min col) (block-max col))
        range-idx (for [r range-row c range-col] (rc-to-index r c))
        block-cells (map board range-idx)]
      (some #{v} block-cells)))

; check if a proposed cell is valid by looking for the value
; in the row, column and board
(defn valid-cell? [board [row col v]]
  (not (or (row-contains? board row v)
           (col-contains? board col v)
           (block-contains? board row col v))))

; add a cell to a board
(defn board-add-cell [board [row col v]]
  (assoc board (rc-to-index row col) v))

; get the next possible moves on a given board
; find the next zero and suggest all possible values of a cell
(defn next-cells [board]
  (let [i (.indexOf board 0)]
    (if (neg? i)
      []
      (map (partial conj (index-to-rc i)) possible-values))))

; get the next valid boards
; append the next possible cells and filter only valid moves
(defn next-boards [board]
  (let [cells (next-cells board)]
    (for [c cells
          :when (valid-cell? board c)]
      (board-add-cell board c))))

; print the board (with index)
(defn print-board [idx board]
  (println idx " -----------")
  (doseq [row (range table-side)
          :let [start (* row table-side)
                 end (+ start table-side)]]
    (println ":" (subvec board start end))))
    

; do a depth first search, printing as we go along
; (because there are *lots* of solutions to a 9x9 puzzle;
; think of the board as an 81-digit number.)
(defn dfs-search [visit-fn board]
  (let [children (next-boards board)
        dfs-search-fn (partial dfs-search visit-fn)]
    (if (is-complete-board? board)
        (visit-fn board))
    (if (= 0 (count children))
      #{board}
      (mapcat dfs-search-fn children))))

; ugly: keep a counter for printing as side-effect of depth first search
(def counter (atom 0N))
(defn print-with-counter [board]
        (do (print-board @counter board)
            (swap! counter inc)))
; main entry point
;#_
(let [solutions (dfs-search print-with-counter empty-board)
      idx-solutions (map-indexed vector solutions)]
     (doseq [s idx-solutions] (apply print-board s)))

; some crude unit tests
;#_
(let [test-board-1 (board-add-cell empty-board [1 1 1])
      test-board-99 (board-add-cell empty-board [2 3 99])
      test-board-full (vec (range 1 (inc (* table-side table-side))))]
 [(row-contains? test-board-99 2 99)
  (col-contains? test-board-99 3 99)
  (board-contains? test-board-full 4 4 16)
  (next-cells test-board-full)
  (next-cells test-board-99)
  (next-boards test-board-1)
  (map is-complete-board? [test-board-1 test-board-full])])
 


