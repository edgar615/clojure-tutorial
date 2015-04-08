(ns clojure-tutorial.api.core.list*)

;list*
;    (list* args)
; (list* a args)
; (list* a b args)
; (list* a b c args)
; (list* a b c d & more)

;Creates a new list containing the items prepended to the rest, the last of which will be treated as a sequence.

(list* 1 [2 3])                                             ;;(1 2 3)
(list* 1 2 [3 4])                                           ;;(1 2 3 4)


;; compared to regular `list` function:
(list 1 [2 3])                                              ;;(1 [2 3])
(list 1 2 [3 4])                                            ;;(1 2 [3 4])

(list* nil [1 2])                                           ;;(nil 1 2)
(list* 1 nil)                                               ;;(1)
(list* 1 ())                                                ;;(1)
(list* () [1 2])                                            ;;(() 1 2)

;list*只是一个帮助函数，来简化创建指定多个值来创建seq的需求，下面两个表达式是等价的
(cons 0 (cons 1 (cons 2 (cons 3 (range 4 10)))))            ;;(0 1 2 3 4 5 6 7 8 9)
(list* 0 1 2 3 (range 4 10))                                ;;(0 1 2 3 4 5 6 7 8 9)
