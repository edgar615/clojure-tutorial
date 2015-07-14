(ns
  ^{:author edgar}
  clojure_tutorial.api.core.aclone)

;aclone
;(aclone array)
;Returns a clone of the Java array. Works on arrays of known types.

(def a (int-array [1 2 3 4]))
(def b (aclone a))
(vec a)
;;[1 2 3 4]
(vec b)
;;[1 2 3 4]

(aset b 0 23)
;;23
(vec b)
;;[23 2 3 4]
(vec a)
;;[1 2 3 4]