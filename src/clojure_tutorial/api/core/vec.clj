(ns clojure-tutorial.api.core.vec)

;vec
;(vec coll)
;Creates a new vector containing the contents of coll. Java arrays will be aliased and should not be modified.
;把传入的集合转换成一个新的vector

(vec (range 5))                                             ;;[0 1 2 3 4]
(vec '(1 2 3))                                              ;;[1 2 3]
(vec #{1 2 3})                                              ;;[1 3 2]
(vec {:a 1 :b 2 :c 3})                                      ;;[[:c 3] [:b 2] [:a 1]]