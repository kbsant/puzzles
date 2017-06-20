(ns ashikasoft.puzzles.towers
    "Ashikasoft - Towers of Hanoi solved with Clojure")


;; Recursive solution to the Towers of Hanoi
;;          |                |                  |
;;         ---               |                  |
;;        -----              |                  |
;;      ---------            |                  |
;;  ................  .................  ................
;; {:id 'a :data [4 3 2 1] } {:id 'b :data [] } {:id 'c :data []}
;; Parameters:
;; This function takes 3 parameters: from, to and tmp, which represent 3 towers.
;; Each tower is represented by a map with keys :id and :data.
;; Each tower is identified by the :id key. This is useful to tell which towers
;; have discs moving between them.
;; The :data key holds a vector representing the pile of discs.
;; Return value:
;; This function returns the solution in a vector of steps.
;; Each step itself is a vector, holding the disc, source tower and destination tower
;; (see Example).
;; Limitations:
;; The "from" tower must be full, while "to" and "tmp" must be empty.
;; The elements in the :data vector are assumed to be in order from bottom to top.
;; Example:
;; user=> a
;; {:id a, :data [4 3 2 1]}
;; user=> b
;; {:id b, :data []}
;; user=> c
;; {:id c, :data []}
;; user=> (towers-rec a b c)
;; [[1 a c] [2 a b] [1 c b] [3 a c] [1 b a] [2 b c] [1 a c] [4 a b] [1 c b] [2 c a] [1 b a] [3 c b] [1 a c] [2 a b] [1 c b]]
;; In the above solution, [1 a c] moves the top disc (1) from tower a to tower c.
;; For reference, code that applies the solution step by step is available in the towers test.
(defn towers-rec
  "Solve the Towers of Hanoi recursively. Discs are represented by a :data vector."
  ([from to tmp solution]
   (if (empty? (:data from))
     solution
     (let [[bottom & remaining] (:data from)
           remaining-from       (assoc from :data remaining)
           solution-tmp         (towers-rec remaining-from tmp to solution)
           next-from            (assoc from :data [])
           next-to              (assoc to :data [bottom])
           next-tmp             (assoc tmp :data remaining)
           next-solution        (conj solution-tmp [bottom (:id from) (:id to)])]
      (towers-rec next-tmp next-to next-from next-solution))))
  ([from to tmp]
   (towers-rec from to tmp [])))


(defn towers-rec-nodata
  "Recursive solution to Towers of Hanoi. This implementation does not use a data vector
  and represents the tower discs by the height of the tower.
  The solution vector only contains the source and destination towers, and not the disc label."
  ([from to tmp height solution]
   (if (zero? height)
     solution
     (let [remaining       (dec height)
           solution-tmp    (towers-rec-nodata from tmp to remaining solution)
           next-solution   (conj solution-tmp ['x (:id from) (:id to)])]
       (towers-rec-nodata tmp to from remaining next-solution))))
  ([from to tmp height]
   (towers-rec-nodata from to tmp height [])))
