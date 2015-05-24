(ns clojure-tutorial.api.core.set-validator!)

;set-validator!
;(set-validator! iref validator-fn)

;Sets the validator-fn for a var/ref/agent/atom. validator-fn must be nil or a side-effect-free fn of one argument, which will be passed the intended new state on any state change.
; If the new state is unacceptable, the validator-fn should return false or throw an exception.
; If the current state (root value if var) is not acceptable to the new validator, an exception will be thrown and the validator will not be changed.

(def sarah (atom {:name "Sarah" :age 25}))
(set-validator! sarah :age)
(swap! sarah dissoc :age)                                   ;;IllegalStateException Invalid reference state
;可以抛出特定的异常来使我们知道为什么修改状态会失败，而不是简单地返回false
(set-validator! sarah #(or (:age %)
                           (throw (IllegalStateException. "People must have ':age'"))))
(swap! sarah dissoc :age)      