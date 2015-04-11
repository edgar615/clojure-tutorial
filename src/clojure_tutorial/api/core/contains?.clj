(ns clojure-tutorial.api.core.contains?)

;contains?
;(contains? coll key)
;Returns true if key is present in the given collection, otherwise returns false.
; Note that for numerically indexed collections like vectors and Java arrays, this tests if the numeric key is within therange of indexes.
; 'contains?' operates constant or logarithmic time;it will not perform a linear search for a value.  See also 'some'.

(contains? {:a 1} :a)    ;=> true
(contains? {:a nil} :a)  ;=> true
(contains? {:a 1} :b)    ;=> false

(contains? [:a :b :c] :b)  ;=> false
(contains? [:a :b :c] 2)   ;=> true
(contains? "f" 0)          ;=> true
(contains? "f" 1)          ;=> false

(contains? #{1 2 3} 2)                                      ;;true

(contains? (java.util.HashMap.) "not-therr")                ;;false

(contains? (into-array [1 2 3]) 0)                          ;;true