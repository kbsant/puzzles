# puzzles

Puzzles solved with Clojure - this project currently contains 2 puzzles:

* Cube puzzle (work in progress)
* Towers of Hanoi

![Gif of Towers](towers-hanoi-cljs.gif?raw=true)

See also the blog at http://www.ashikasoft.com

## Usage

* How to include it in your project

To include it in your project, download it from github and install it in your local repository with lein:

    lein install

Then include it in project.clj:

    [ashikasoft/puzzles "0.1.0-SNAPSHOT]

* Cube puzzle

```
    ;;              7  6  5
    ;;              8  B  4
    ;;              1  2  3
    ;;
    ;;   7  8  1    1  2  3    3  4  5
    ;;  19  N  9    9  W 10   10  R 20
    ;;  11 18 17   17 16 15   15 14 13
    ;;
    ;;             17 16 15
    ;;             18  G 14
    ;;             11 12 13             
    ;;
    ;;             11 12 13
    ;;             19  Y 20
    ;;              7  6  5
```
A six-sided cube has one color for each side: Blue, oraNge, White, Red, Green and Yellow.
Each side (face) is divided into 9 tiled sub-cubes, forming rows and columns.
Sub-cubes along the edges and corners are shared across sides, i.e. tiles with the same number refer to the same sub-cube.  
Rows and columns on a cube may be rotated, causing the colors to be scrambled.
The dcube program de-scrambles this by searching backwards from the initial pristine state to the scrambled state. 

* Towers of Hanoi

```
    ;;
    ;;          |                |                  |
    ;;         ---               |                  |
    ;;        -----              |     I            |
    ;;      ---------            |                  |
    ;;  ................  .................  ................
    ;;         src              dst                tmp
```

A recursive implementation of Towers of Hanoi is available.
It is written in platform-independent clojure and works without side-effects:
maps serve as input, while a the output solution is a series of steps represented
by a vector:

    ;; Call with input:
    ;; (puzzles/towers-rec
    ;;   {:id 'a :data [4 3 2 1] }
    ;;   {:id 'b :data [] }
    ;;   {:id 'c :data []} )
    ;; Output:
    ;; [[a c 1] [a b 2] [c b 1] [a c 3] [b a 1] [b c 2] [a c 1] [a b 4] [c b 1] [c a 2] [b a 1] [c b 3] [a c 1] [a b 2] [c b 1]]

To apply the solution vector to the tower data, use the towers/move function:

    ;; Move a disc according to the first step of the solution
    (let [a              {:id :a :data [4 3 2 1]}
          b              {:id :b :data []}
          c              {:id :c :data []}
          initial-state  {:a a :b b :c c}
          solution-steps (towers-rec a b c)
          [src dst]      (first solution-steps)]
        (move initial-state src dst))

For an example using a loop for all of the solution steps, see towers_test.

Clojurescript demo: http://www.ashikasoft.com/demo source coming soon




## License

Copyright © 2018 Kean Santos

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
