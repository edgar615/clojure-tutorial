(ns clojure-tutorial.api.core.take-nth)

;take-nth
;(take-nth n coll)
;Returns a lazy seq of every nth item in coll.

(take-nth 1 (range 10))                                     ;;(0 1 2 3 4 5 6 7 8 9)
(take-nth 2 (range 10))                                     ;;(0 2 4 6 8)
(take-nth 3 (range 10))                                     ;;(0 3 6 9)