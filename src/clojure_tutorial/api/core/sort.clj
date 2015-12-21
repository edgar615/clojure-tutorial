;; sort
;; (sort coll)
;; (sort comp coll)

;Returns a sorted sequence of the items in coll. If no comparator is supplied, uses compare.
; comparator must implement java.util.Comparator.  If coll is a Java array, it will be modified.
; To avoid this, sort a copy of the array.

(sort [3 2 1 4])                                            ;;(1 2 3 4)

(sort < (repeatedly 10 #(rand-int 100)))                    ;;(3 6 8 19 37 61 68 74 83 89)

(sort > (vals {:foo 5, :bar 2, :baz 10}))                   ;;(10 5 2)

(sort #(compare (first %1) (first %2)) {:b 1 :c 3 :a  2})   ;;([:a 2] [:b 1] [:c 3])

;; liked this
(sort-by first {:b 1 :c 3 :a  2})
;; ([:a 2] [:b 1] [:c 3])
