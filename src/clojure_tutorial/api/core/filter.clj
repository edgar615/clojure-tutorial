(ns clojure-tutorial.api.core.filter)

;filter
;(filter pred coll)

;Returns a lazy sequence of the items in coll for which (pred item) returns true. pred must be free of side-effects.

(filter (fn [x]
          (= (count x) 1))
        ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""])     ;;("a" "b" "n" "f" "q")