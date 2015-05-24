(ns clojure-tutorial.api.core.compare-and-set!)

;compare-and-set!
;(compare-and-set! atom oldval newval)

;Atomically sets the value of atom to newval if and only if the current value of the atom is identical to oldval.
; Returns true if set happened, else false

;如果你已经知道要修改的原子类型的值是什么的话，可以使用这个函数，如果修改成功的话，这个函数会返回true
(compare-and-set! xs :wrong "new value")
;;false
(compare-and-set! xs @xs "new value")
;;true
@xs
;;"new value"

;compare-and-set!不能使用值语义，它要求atom的值跟你传给它的第二个参数必须一样
(def xs (atom #{1 2}))
(compare-and-set! xs #{1 2} "new value")
;;false