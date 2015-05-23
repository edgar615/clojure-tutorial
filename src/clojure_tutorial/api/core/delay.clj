(ns clojure-tutorial.api.core.delay)

;delay
;(delay & body)
;Takes a body of expressions and yields a Delay object that will invoke the body only the first time it is forced (with force or deref/@),
; and will cache the result and return it on all subsequent force calls. See also - realized?
;delay对它所包含的代码只执行一次，然后把返回值缓存起来。后面所有使用deref对它进行的访问抖森直接返回的，而不用再去执行它所包含的代码。
(def my-delay (delay (println "did some work") 100))

(realized? my-delay)
;;false
@my-delay
;;did some work
;;100


(realized? my-delay)
;;true
@my-delay
;;100

