(ns clojure-tutorial.api.core.set)
;set
;;(set coll)
;Returns a set of the distinct elements of coll.
;把其他类型的集合转换成一个set

(set '(1 1 2 3 2 4 5 5))                                    ;;#{1 4 3 2 5}
(set [1 1 2 3 2 4 5 5])                                     ;;#{1 4 3 2 5}
(set "abcd")                                                ;;#{\a \b \c \d}
(set {:one 1 :two 2 :three 3})                              ;;#{[:three 3] [:two 2] [:one 1]}
