(ns anketeur.puzzles.foldr)

(defn foldr-rev 
  "foldr implementation that uses reverse"
  [f vs z] 
  (reduce #(f %2 %1) z (reverse vs)))

(defn foldr-rec
 "foldr implementation that uses recursion with strict evaluation" 
  [f [x & xs ] z] 
  (if-not (nil? x) 
    (f x (foldr-rec f xs z)) 
    z))


(defn foldr-delay
 "foldr using delay. If f must evaluate its second argument,
 it should call 'force'. " 
  [f xs z]
    (if-let [s (seq xs)]
      (f (first s) (delay (foldr-delay f (rest s) z)))
      z))


#_(
ersbane.core=>  (foldr-delay* #(if (< %1 1000) (+ %1 @%2) 0) (range  1000000000) 0)
#object[clojure.lang.Delay 0x41fb404 {:status :pending, :val nil}]
ersbane.core=>  (foldr-delay #(if (< %1 1000) (+ %1 @%2) 0) (range  1000000000) 0)
499500
ersbane.core=>  (foldr-rec #(if (< %1 1000) (+ %1 %2) 0) (range  1000000000) 0)

StackOverflowError   clojure.lang.LongRange.next (LongRange.java:142)
ersbane.core=>  (foldr-rev #(if (< %1 1000) (+ %1 %2) 0) (range  1000000000) 0)


   )
;; TODO this always throws a stack overflow during compilation
(defmacro foldr-recm
 "foldr implementation that uses recursion with strict evaluation" 
  [f  xs z] 
  `(if-let [s# (seq ~xs)] 
    (~f (first s#) (foldr-recm ~f (rest s#) ~z)) 
    ~z))

(defn foldr-lazy
  "lazy implementation of foldr"
  ;; TODO fix StackOverflowError 
  ;; (def lv (foldr-lazy vector (range 10000) []))
  ;; (take 2 lv)
  ;; StackOverflowError   clojure.lang.PersistentHashMap$BitmapIndexedNode.ensureEditable (PersistentHashMap.java:806)
  [f xs z]
  (lazy-seq
    (if-let [s (seq xs)]
      (f (first s) (foldr-lazy f (rest s) z))
      z)))


