(ns ashikasoft.puzzles.dcubej
  (:require 
    [ashikasoft.puzzles.dcube :as dcube]
    [medley.core :as medley])
  (:import (java.util ArrayDeque HashSet)))

;; Warning 0: BFS only finds complete solutions till 7 moves. Somewhere at 8 moves,
;; the garbage collector gets so busy that the jvm throws an error:
;; OutOfMemoryError GC overhead limit exceeded;;
;;
;; This implementation attempts to reduce overhead by using a java queue and history set.
;;

(defn filter-previous-nset
  "Filter out items in the list if they were previously encountered."
  [previous-set next-list]
  (remove #(.contains previous-set (first %)) next-list))

(defn solve
  "Starting at the initial cube, generate the steps needed to get to any
   of the target cubes.
   The target cubes argument should be a set containing one or more vectors
   of the standard length of 20, same as the initial cube.
   An empty initial search structure and a search size limit may be optionally supplied."
  ([target-cubes]
   (solve 1000000 target-cubes))
  ([max-struct target-cubes]
   (solve max-struct dcube/initial-cube target-cubes))
  ([max-struct source-cube target-cubes]
   (solve (ArrayDeque.) max-struct source-cube target-cubes))
  ([search-struct max-struct source-cube target-cubes]
   {:pre [(set? target-cubes)
          (= (count dcube/initial-cube)
             (count (into #{} source-cube))
             (let [target-sizes (into #{} (map (comp count set)) target-cubes)]
               (when (= 1 (count target-sizes))
                 (first target-sizes))))
          (empty? search-struct)]}
   (loop [q (do (.add search-struct [source-cube '()]) search-struct)
          past (let [p (HashSet. 2000000)] (.add p source-cube) p)]
     (let [[cube steps] (.poll q)]
       (cond
         (not cube)
         [nil "empty search result"]
         (target-cubes cube)
         steps
         (> (count q) max-struct)
         [nil (str "search limit exceeded: " max-struct)]
         :else
         (let [next-list (dcube/next-steps cube steps)
               filtered (filter-previous-nset past next-list)
               next-past (do (.addAll past (map first filtered)) past)
               next-queue (do (.addAll q filtered) q)]
           (when (= 0 (rem (count q) 10000)) (println (str (count q) " : " cube ", " (count steps) ", " (count next-past))))
           (recur next-queue next-past)))))))

