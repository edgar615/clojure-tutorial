(ns clojure-tutorial.api.core.if-let)

;if-let
;(if-let bindings then)
;(if-let bindings then else & oldform)

;bindings => binding-form test
; If test is true, evaluates then with binding-form bound to the value oftest, if not, yields else

(if-let [x false y true]
  "then"
  "else")                                                   ;IllegalArgumentException if-let requires exactly 2 forms in binding vector in user:1

(if-let [x false]
  "then"
  "else")                                                   ;else

(if-let [x true]
  "then"
  "else")                                                   ;then

(defn if-let-demo
  [arg]
  (if-let [x arg]
    "then"
    "else"))
(if-let-demo 1)                                             ;then
(if-let-demo nil)                                           ;else
(if-let-demo false)                                         ;else