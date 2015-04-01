(ns clojure-tutorial.api.core.repeatedly)

;repeatedly
;    (repeatedly f)
;(repeatedly n f)

;Takes a function of no args, presumably with side effects, and returns an infinite (or length n if supplied) lazy sequence of calls to it

; these two functions are equivalent
(take 5 (repeatedly #(rand-int 10)))                        ;;(2 7 9 2 9)
(repeatedly 5 #(rand-int 10))                               ;;(9 3 8 7 8)