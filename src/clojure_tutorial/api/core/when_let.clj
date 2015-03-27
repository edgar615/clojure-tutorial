(ns clojure-tutorial.api.core.when-let)

;when-let
;(when-let bindings & body)
;bindings => binding-form test
; When test is true, evaluates body with binding-form bound to the value of test

(when [x true y false]
  "then"
  "else")                                                   ;;CompilerException java.lang.RuntimeException: Unable to resolve symbol: y in this context

(defn drop-one
  [coll]
  (when-let [s (seq coll)]
    (rest s)))

(drop-one [1 2 3])                                          ;[2 3]
(drop-one [])                                               ;nil