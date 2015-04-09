(ns clojure-tutorial.api.core.take-while)
;take-while
;(take-while pred coll)
;Returns a lazy sequence of successive items from coll while (pred item) returns true. pred must be free of side-effects.

(take-while neg? [-2 -1 0 1 2 3])                           ;;(-2 -1)

(take-while neg? [-2 -1 0 -1 -2 3])                         ;;(-2 -1)
(take-while neg? [1 2 3 ])                                  ;;()
(take-while neg? [])                                  ;;()
(take-while neg? nil)                                  ;;()