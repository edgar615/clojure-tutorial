(ns clojure-tutorial.api.core.rseq)

;rseq
;(rseq rev)

;Returns, in constant time, a seq of the items in rev (which can be a vector or sorted-map), in reverse order. If rev is empty returns nil

;rseq函数可以在常量时间内反序返回一个集合的元素

(vec (range 10))                                            ;;[0 1 2 3 4 5 6 7 8 9]
(rseq (vec (range 10)))                                     ;;(9 8 7 6 5 4 3 2 1 0)

(sorted-map :z 1 :b 2 :c 3)                                 ;;{:b 2, :c 3, :z 1}
(rseq (sorted-map :z 1 :b 2 :c 3))                          ;;([:z 1] [:c 3] [:b 2])
