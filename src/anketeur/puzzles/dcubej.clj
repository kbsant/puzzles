(ns anketeur.puzzles.dcubej
  (:import (java.util Set Queue ArrayDeque HashSet)))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

;; Warning 0: BFS only finds complete solutions till 7 moves. Somewhere at 8 moves,
;; the garbage collector gets so busy that the jvm throws an error:
;; OutOfMemoryError GC overhead limit exceeded;;
;;
;; This implementation attempts to reduce overhead by using a java queue and history set.
;;
(def initial-cube
  "Initial cube data - sorted position."
  (int-array
    [
            7  6  5
            8     4
            1  2  3
      19    9    10     20
           17 16 15
           18    14
           11 12 13]))

(defmacro aget-set
  "Copy to the output indices from the source indices inline."
  [out out-idxs in in-idxs]
  (let [body (for [i (range (count out-idxs))]
                `(aset ~out ~(nth out-idxs i) (aget ~in ~(nth in-idxs i))))]
    (concat (cons 'do body) `(~out))))

;; indexes: 0 1 2 3 4 5 6 7
;; values:  7 6 5 8 4 1 2 3  ->  1 8 7 2 6 3 4 5
(defn rtop-
  "Rotate the top row to the left."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [0 1 2 3 4 5 6 7]
      in  [5 3 0 6 1 7 4 2])))

;; indexes: 0 1 2 3 4 5 6 7
;; values:  7 6 5 8 4 1 2 3  ->  5 4 3 6 2 7 8 1
(defn rtop+
  "Rotate the top row to the right."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [0 1 2 3 4 5 6 7]
      in  [2 4 7 1 6 0 3 5])))

;; indexes: 12 13 14 15 16 17 18 19
;; values:  17 16 15 18 14 11 12 13 -> 11 18 17 12 16 13 14 15
(defn rbottom-
  "Rotate the bottom row to the left."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [12 13 14 15 16 17 18 19]
      in  [17 15 12 18 13 19 16 14])))

;; indexes: 12 13 14 15 16 17 18 19
;; values:  17 16 15 18 14 11 12 13 -> 15 14 13 16 12 17 18 11
(defn rbottom+
  "Rotate the bottom row to the right."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [12 13 14 15 16 17 18 19]
      in  [14 16 19 13 18 12 15 17])))

;; indexes: 0  3  5  8  9 12 15 17
;; values:  7  8  1 19  9 17 18 11 -> 1 9 17 8 18 11 19 7
(defn rleft-
  "Rotate the left column upward."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [0 3 5 8 9 12 15 17]
      in  [5 9 12 3 15 17 8 0])))

;; indexes: 0  3  5  8  9 12 15 17
;; values:  7  8  1 19  9 17 18 11 -> 11 19 7 18 8 1 9 17
(defn rleft+
  "Rotate the left column downward."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [0 3 5 8 9 12 15 17]
      in  [17 8 0 15 3 5 9 12])))

;; indexes: 2 4 7 10 11 14 16 19
;; values:  5 4 3 10 20 15 14 13 -> 3 10 15 14 4 13 20 5
(defn rright-
  "Rotate the right column upward."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [2 4 7 10 11 14 16 19]
      in  [7 10 14 16 4 19 11 2])))

;; indexes: 2 4 7 10 11 14 16 19
;; values:  5 4 3 10 20 15 14 13 -> 13 20 5 4 14 3 10 15
(defn rright+
  "Rotate the right column downward."
  ^ints
  [^ints in]
  (let [out (aclone in)]
    (aget-set
      out [2 4 7 10 11 14 16 19]
      in  [19 11 2 4 16 7 10 14])))

(def ops-complements
  "A map of operations and their complements. Uses vars so that their names are available."
  {#'rtop- #'rtop+
   #'rtop+ #'rtop-
   #'rbottom- #'rbottom+
   #'rbottom+ #'rbottom-
   #'rleft- #'rleft+
   #'rleft+ #'rleft-
   #'rright- #'rright+
   #'rright+ #'rright-})

(def ops
  "A collection of operations"
  (keys ops-complements))

(defn next-steps
  "given a state and its history, return all the next steps."
  [^ints cube steps]
  (map #(vector (%1 cube) (conj steps %1)) ops))

(defn filter-previous
  "Filter out items in the list if they were previously encountered."
  [previous-set next-list]
  (remove (comp previous-set vec first) next-list))

(defn filter-previous-nset
  "Filter out items in the list if they were previously encountered."
  [^Set previous-set next-list]
  (remove #(.contains previous-set (vec (first %))) next-list))

(defn solve
  "Starting at the initial cube, generate the steps needed to get to any
   of the target cubes.
   The target cubes argument should be a set containing one or more vectors
   of the standard length of 20, same as the initial cube.
   An empty initial search structure and a search size limit may be optionally supplied."
  ([target-cubes]
   (solve 1000000 target-cubes))
  ([max-struct target-cubes]
   (solve max-struct initial-cube target-cubes))
  ([max-struct source-cube target-cubes]
   (solve (ArrayDeque.) max-struct source-cube target-cubes))
  ([^Queue search-struct ^long max-struct ^ints source-cube ^Set target-cubes]
   {:pre [(= (count initial-cube)
             (count (into #{} source-cube))
             (let [target-sizes (into #{} (map (comp count set)) target-cubes)]
               (when (= 1 (count target-sizes))
                 (first target-sizes))))
          (empty? search-struct)]}
   (loop [q (do (.add search-struct [source-cube '()]) search-struct)
          past (let [p (HashSet. 2000000)] (.add p (vec source-cube)) p)]
     (let [[cube steps] (.poll q)]
       (cond
         (not cube)
         {:error-message "empty search result"}
         (.contains target-cubes (vec cube))
         {:steps steps
          :target (vec cube)}
         (> (count q) max-struct)
         {:error-message (str "search limit exceeded: " max-struct)
          :past past}
         :else
         (let [next-list (next-steps cube steps)
               filtered (filter-previous-nset past next-list)
               next-past (do (.addAll past (map (comp vec first) filtered)) past)
               next-queue (do (.addAll q filtered) q)]
           (when (= 0 (rem (count q) 10000)) (println (str (count q) " : " cube ", " (count steps) ", " (count next-past))))
           (recur next-queue next-past)))))))

