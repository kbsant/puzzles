# puzzles

Puzzles solved with Clojure - this project currently contains 1 puzzle:
* Towers of Hanoi

See the blog at http://www.ashikasoft.com

## Usage

* How to include it in your project
To include it in your project, download it from github and install it in your local repository with lein:

    lein install

Then include it in project.clj:

    [ashikasoft/puzzles "0.1.0-SNAPSHOT]

* Towers of Hanoi

    ;;          |                |                  |
    ;;         ---               |                  |
    ;;        -----              |     I            |
    ;;      ---------            |                  |
    ;;  ................  .................  ................

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
    ;; [[1 a c] [2 a b] [1 c b] [3 a c] [1 b a] [2 b c] [1 a c] [4 a b] [1 c b] [2 c a] [1 b a] [3 c b] [1 a c] [2 a b] [1 c b]]

For an example of re-applying the solution vector to the original data, see the unit test.
Clojurescript demo: coming soon



## License

Copyright © 2017 Kean Santos

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
