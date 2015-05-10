(ns clojure-tutorial.api.core.vector?)

;vector?
;(vector? x)
;Return true if x implements IPersistentVector
(vector? [1 2 3])                                           ;;true
(vector? '(1 2 3))                                          ;;false