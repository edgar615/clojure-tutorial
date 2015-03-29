(ns clojure-tutorial.api.core.iterate)

;iterate
;(iterate f x)

;Returns a lazy sequence of x, (f x), (f (f x)) etc. f must be free of side-effects

;返回的是惰性序列
(iterate inc 5)                                             ;;(5 6 7 8 9 10 11 12 13 14 15 ... n
(take 5 (iterate inc 5))                                    ;;(5 6 7 8 9)
(take 10 (iterate (partial + 2) 0))                         ;;(0 2 4 6 8 10 12 14 16 18)