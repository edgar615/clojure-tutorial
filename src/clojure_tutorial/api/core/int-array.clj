(ns
  ^{:author edgar}
  clojure_tutorial.api.core.int-array)

;int-array
;    (int-array size-or-seq)
;(int-array size init-val-or-seq)
;Creates an array of ints

(int-array '(1 2 3))
;;#<int[] [I@4982cc36>

;; if you need a certain size, with a constant initial value
(aget (int-array 5 1) 4)
;;1

(alength (int-array 5 (range 10)))
;;5