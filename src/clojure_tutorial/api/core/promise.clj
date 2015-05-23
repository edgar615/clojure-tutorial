(ns clojure-tutorial.api.core.promise)

;promise
;(promise)

;Returns a promise object that can be read with deref/@, and set, once only, with deliver.
; Calls to deref/@ prior to delivery will block, unless the variant of deref with timeout is used.
; All subsequent derefs will return the same delivered value without blocking. See also - realized?.

;一个promise也可以被解引用，解引用的时候可以传入一个超时的参数，解引用的时候如果这个promise还没有，那么解引用的代码会阻塞直到这个值准备好。
;但是promise在创建的时候并不会指定任何代码活摘函数来最终产生出它们的值
(def p (promise))
;promise开始是一个空容器，当程序运行的某个时间点，这个promise会被填充入一个值——通过deliver函数
(realized? p)                                               ;;false
(deliver p 42)                                              ;;#<core$promise$reify__6363@6f0ebf67: 42>
(realized? p)                                               ;;true
@p                                                          ;;42
;promise跟一个一次性的，单值的管道类似，

(def a (promise))
(def b (promise))
(def c (promise))
(future
  (deliver c (+ @a @b))
  (println "Delivery complete"))
;上述代码，c的值直到a和b的值都有了才会被传入

;超时值
(def p2 (promise))
(deref p2 3000 :ignore)                                     ;;:ignore