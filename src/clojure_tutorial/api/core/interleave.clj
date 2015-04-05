(ns clojure-tutorial.api.core.interleave)

;interleave
;    (interleave)(interleave c1)(interleave c1 c2)(interleave c1 c2 & colls)

;Returns a lazy seq of the first item in each coll, then the second etc.

(interleave [:a :b :c] [1 2 3])                             ;;(:a 1 :b 2 :c 3)
(interleave [:a :b :c] [1 2])                               ;;(:a 1 :b 2)

