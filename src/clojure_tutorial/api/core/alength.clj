(ns
  ^{:author edgar}
  clojure_tutorial.api.core.alength)

;alength
;(alength array)
;Returns the length of the Java array. Works on arrays of all types.

(def a (make-array Integer/TYPE 10))
(alength a)
;;10
