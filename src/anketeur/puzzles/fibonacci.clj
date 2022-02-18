(ns anketeur.puzzles.fibonacci)

(def fib-seq
  (lazy-cat [0 1] (map + fib-seq (rest fib-seq))))

(comment
  "Repl session"
  (take 20 fib-seq)
  (take 5 (drop 5 fib-seq))
  ;;
  )

