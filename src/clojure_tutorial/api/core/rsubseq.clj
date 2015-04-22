(ns clojure-tutorial.api.core.rsubseq)

;rsubseq
;    (rsubseq sc test key)
; (rsubseq sc start-test start-key end-test end-key)

;sc must be a sorted collection, test(s) one of <, <=, > or >=. Returns a reverse seq of those entries with keys ek for which (test (.. sc comparator (compare ek key)) 0) is true

;rsubseq跟subseq类似，但是返回的元素是反序的
(subseq (sorted-set 1 2 3 4) > 2)                           ;;(3 4)
(rsubseq (sorted-set 1 2 3 4) > 2)                           ;;(4 3)

(subseq (sorted-map :z 5 :x 9 :y 0 :b 2 :a 3 :z 5) > :b <= :y) ;;([:x 9] [:y 0])
(rsubseq (sorted-map :z 5 :x 9 :y 0 :b 2 :a 3 :z 5) > :b <= :y) ;;([:y 0] [:x 9])


