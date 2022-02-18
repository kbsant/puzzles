(ns anketeur.puzzles.coffeelike
  (:require [clojure.string :as string]))

(defn at [s x] (.charAt s x))

(defn coffeelike? [s]
  (when (= 6 (.length s))
    (and (= (at s 2) (at s 3))
         (= (at s 4) (at s 5)))))

(defn main []
  (println (if (coffeelike? (slurp *in*))
             "Yes"
             "No")))
