(ns clojure-tutorial.api.core.future)

;future
;(future & body)

;Takes a body of expressions and yields a future object that will invoke the body in another thread, and will cache the result and return it on all subsequent calls to deref/@.
; If the computation has not yet finished, calls to deref/@ will block, unless the variant of deref with timeout is used. See also - realized?.

;future会在另一个线程里面执行它所包含的代码
(def f (future (Thread/sleep 10000) (println "done") 100))

@f
;;done
;;100

@f
;;100

;在解引用一个future的时候，可以指定一个超时时间以及一个“超时值”
(deref (future (Thread/sleep 5000) :done!)
       1000
       :impatitent!)
;;:impatitent!
