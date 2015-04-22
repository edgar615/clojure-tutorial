(ns clojure-tutorial.api.core.sorted-set-by)

;sorted-set-by
;(sorted-set-by comparator & keys)

;Returns a new sorted set with supplied keys, using the supplied comparator.  Any equal keys are handled as if by repeated uses of conj.

(sorted-set-by > 3 5 8 2 1)                                 ;;#{8 5 3 2 1}