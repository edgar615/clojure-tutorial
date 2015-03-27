(ns clojure-tutorial.api.core.if-some)

;if-some
;    (if-some bindings then)
;(if-some bindings then else & oldform)

;bindings => binding-form test
;If test is not nil, evaluates then with binding-form bound to the value of test, if not, yields else

(if-some [a 10] :true :false)                               ;;:true
(if-some [a true] :true :false)                             ;;:true
(if-some [a false] :true :false)                            ;;:true
(if-some [a nil] :true :false)                              ;;:false

;与if-let比较
(if-let [a 10] :true :false)                               ;;:true
(if-let [a true] :true :false)                             ;;:true
(if-let [a false] :true :false)                            ;;:false
(if-let [a nil] :true :false)                              ;;:false
