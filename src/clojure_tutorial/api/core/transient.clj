(ns clojure-tutorial.api.core.transient)
;transient
;(transient coll)
;Returns a new, transient version of the collection, in constant time.

(def x (transient []))
(def y (conj! x 1))
(count y)                                                   ;;1
(count x)                                                   ;;1

(def v [1 2])
(def tv (transient v))
;;用来产生易变集合的持久性集合是不会被影响的
(conj v 3)                                                  ;;[1 2 3]
