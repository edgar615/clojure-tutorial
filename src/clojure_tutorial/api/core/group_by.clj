(ns clojure-tutorial.api.core.group-by)
;group-by
;(group-by f coll)
;Returns a map of the elements of coll keyed by the result of f on each element.
; The value at each key will be a vector of the corresponding elements, in the order they appeared in coll.

(group-by count ["a" "as" "asd" "aa" "asdf" "qwer"])        ;;{1 ["a"], 2 ["as" "aa"], 3 ["asd"], 4 ["asdf" "qwer"]}
(group-by odd? (range 10))                                  ;;{false [0 2 4 6 8], true [1 3 5 7 9]}