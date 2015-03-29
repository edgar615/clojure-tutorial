(ns clojure-tutorial.api.core.not=)

;not=
;    (not= x)
;(not= x y)
;(not= x y & more)

;Same as (not (= obj1 obj2))
(not= 1 1)                                                  ;;false
(not= 1 2)                                                  ;;true
(not= true true true)                                       ;;false
(not= true true false)                                      ;;true
