(ns clojure-tutorial.api.core.partial)

;partial
;(partial f)
;(partial f arg1)
;(partial f arg1 arg2)
;(partial f arg1 arg2 arg3)
;(partial f arg1 arg2 arg3 & more)

;Takes a function f and fewer than the normal arguments to f, and returns a fn that takes a variable number of additional args. When
;called, the returned function calls f with args + additional args.

;偏函数：把函数的一部分参数传给一个函数，这样创建一个新的函数，这个函数需要的参数就是你没有传给那个函数的那些剩余函数
;预先为某个函数加上个别参数，等到函数正式被调用的时候把剩下的参数补上

(def hundred-times (partial * 100))
(hundred-times 5)                                           ;;500
(hundred-times 4 5 6)                                       ;;12000

(def add-hundred (partial + 100))
(add-hundred 5)                                             ;;105

(def substract-from-hundred (partial - 100))
(substract-from-hundred 10)                                  ;;90
(substract-from-hundred 10 20)                                ;;70