(ns clojure-tutorial.api.core.sorted-map-by)

;sorted-map-by
;(sorted-map-by comparator & keyvals)
;keyval => key val
;Returns a new sorted map with supplied mappings, using the supplied comparator.
;If any keys are equal, they are handled as if by repeated uses of assoc.

(sorted-map-by > 1 "a" 2 "b" 3 "c")                         ;;{3 "c", 2 "b", 1 "a"}

(sorted-map-by compare :z 5 :x 9 :y 0 :b 2 :a 3 :c 4)       ;;{:a 3, :b 2, :c 4, :x 9, :y 0, :z 5}
(sorted-map-by (comp - compare) :z 5 :x 9 :y 0 :b 2 :a 3 :c 4) ;;{:z 5, :y 0, :x 9, :c 4, :b 2, :a 3}

