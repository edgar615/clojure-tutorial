(ns clojure-tutorial.api.core.subseq)

;subseq
;    (subseq sc test key)
;   (subseq sc start-test start-key end-test end-key)

;sc must be a sorted collection, test(s) one of <, <=, > or >=. Returns a seq of those entries with keys ek for which (test (.. sc comparator (compare ek key)) 0) is true

;subseq可返回一个集合的某一个区间的元素的序列
(subseq [1 2 3 4] > 2)                                      ;;ClassCastException clojure.lang.PersistentVector cannot be cast to clojure.lang.Sorted

(subseq (sorted-set 1 2 3 4) > 2)                           ;;(3 4)
(subseq (sorted-map :z 5 :x 9 :y 0 :b 2 :a 3 :z 5) > :b <= :y) ;;([:x 9] [:y 0])
