(ns clojure-tutorial.api.core.cons)

;cons
;(cons x seq)
;Returns a new seq where x is the first element and seq is the rest.

;cons接受两个参数，第一个参数是一个值作为新seq的头，第二个参数是一个集合，作为新seq的尾巴

;; prepend 1 to a list
(cons 1 '(2 3 4 5 6))                                       ;;(1 2 3 4 5 6)
;; notice that the first item is not expanded
(cons [1 2] '(3 4 5 6))                                     ;;([1 2] 3 4 5 6)

(cons 0 (range 1 5))                                        ;;(0 1 2 3 4)
;cons始终把第一个参数值加入到第二个集合参数的头上去，而不管第二个参数具体的集合类型，所以它跟conj的作用是不同的

(cons :a [:b :c :d])                                        ;;(:a :b :c :d)

