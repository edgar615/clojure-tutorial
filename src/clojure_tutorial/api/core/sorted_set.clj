(ns clojure-tutorial.api.core.sorted-set)

;sorted-set
;(sorted-set & keys)

;Returns a new sorted set with supplied keys.  Any equal keys are handled as if by repeated uses of conj.

(sorted-set 3 2 1)                                          ;;#{1 2 3}
(sorted-set 1 3 2 1)                                        ;;#{1 2 3}
#{2 1 3}                                                    ;;#{1 3 2}
(apply sorted-set #{2 1 3})                                 ;;#{1 2 3}