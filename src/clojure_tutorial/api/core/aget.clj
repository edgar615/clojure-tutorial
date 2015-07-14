(ns
  ^{:author edgar}
  clojure_tutorial.api.core.aget)

;aget
;    (aget array idx)
;(aget array idx & idxs)

;Returns the value at the index/indices. Works on Java arrays of all types.

(def a1 (double-array '(1.0 2.0 3.0 4.0)))

(def a2 (int-array '(9 8 7 6)))

(aget a1 2)
;;3.0

(aget a2 0)
;;0

(def a3 (make-array Integer/TYPE 100 100))

(aget a3 10 10)
;;0