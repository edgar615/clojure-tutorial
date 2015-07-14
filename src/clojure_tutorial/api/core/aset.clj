(ns
  ^{:author edgar}
  clojure_tutorial.api.core.aset)

;aset
;    (aset array idx val)
;(aset array idx idx2 & idxv)

;Sets the value at the index/indices. Works on Java arrays of reference types. Returns val.

(def a (int-array [1 2 3 4]))
(vec a)
;;[1 2 3 4]

(aset a 0 23)
;;23

(vec a)
;;[23 2 3 4]