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
;; This function takes 3 parameters: src, dst and tmp, which represent 3 towers.
;; Each tower is represented by a map with keys :id and :data.
;; Each tower is identified by the :id key. This is useful to tell which towers
;; have discs moving between them.
;; The :data key holds a vector representing the pile of discs.
;; Return value:
;; This function returns the solution in a vector of steps.
;; Each step itself is a vector, holding the disc, source tower and destination tower
;; (see Example).
;; Limitations:
;; The "src" tower must be full, while "dst" and "tmp" must be empty.
;; The elements in the :data vector are assumed to be in order from bottom to top.
;; Example:
;; user=> a
;; {:id a, :data [4 3 2 1]}
;; user=> b
;; {:id b, :data []}
;; user=> c
;; {:id c, :data []}
;; user=> (towers-rec a b c)
;; [[a c 1] [a b 2] [c b 1] [a c 3] [b a 1] [b c 2] [a c 1] [a b 4] [c b 1] [c a 2] [b a 1] [c b 3] [a c 1] [a b 2] [c b 1]]
;; In the above solution, [a c 1] moves the top disc (1) from tower a to tower c.
;; For reference, code that applies the solution step by step is available in the towers test.
(defn towers-rec
  "Solve the Towers of Hanoi recursively. Discs are represented by a :data vector."
  ([src dst tmp solution]
   (if (empty? (:data src))
     solution
     (let [[bottom & remaining] (:data src)
           remaining-src        (assoc src :data remaining)
           solution-tmp         (towers-rec remaining-src tmp dst solution)
           next-src             (assoc src :data [])
           next-dst             (assoc dst :data [bottom])
           next-tmp             (assoc tmp :data remaining)
           next-solution        (conj solution-tmp [(:id src) (:id dst) bottom])]
      (towers-rec next-tmp next-dst next-src next-solution))))
  ([src dst tmp]
   (towers-rec src dst tmp [])))

;; Instead of representing towers with maps, the solution can be found by simply using the height
;; of the tower. This function takes the tower ids only, and the height of the source tower.
(defn towers-rec-height
  "Recursive solution to Towers of Hanoi. This implementation does not use a data vector
  and represents the tower discs by the height of the tower.
  The solution vector only contains the source and destination towers, and not the disc labels."
  ([src dst tmp height solution]
   (if (zero? height)
     solution
     (let [remaining       (dec height)
           solution-tmp    (towers-rec-height src tmp dst remaining solution)
           next-solution   (conj solution-tmp [src dst])]
       (towers-rec-height tmp dst src remaining next-solution))))
  ([src dst tmp height]
   (towers-rec-height src dst tmp height [])))

;; The tail-recursive solution puts the parameters into a stack
(defn towers-stack-height
  "Tail-recursive solution to Towers of Hanoi, using a stack. Like towers-rec-height, this implementation
  does not use a data vector, but represents the tower discs only by the height of the tower.
  The solution vector only contains the source and destination towers, and not the disc labels."
  ([stack solution]
   (if (empty? stack)
     solution
     (let [[src dst tmp height] (peek stack)
           rest-stack           (pop stack)
           remaining            (dec height)]
       (cond
         (zero? height)
         (recur rest-stack solution)
         (zero? remaining)
         (recur rest-stack (conj solution [src dst]))
         :else
         (let [src-to-tmp [src tmp dst remaining]   ;; move all but last to tmp
               src-to-dst [src dst tmp 1]           ;; move last to dst
               tmp-to-dst [tmp dst src remaining]]  ;; move move from tmp to dst
            (recur (conj rest-stack tmp-to-dst src-to-dst src-to-tmp) solution))))))
  ([src dst tmp height]
   (towers-stack-height [[src dst tmp height]] [])))
