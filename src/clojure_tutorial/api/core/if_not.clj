(ns clojure-tutorial.api.core.if-not)

;(if-not test then)
;(if-not test then else)
(if-not "hi" \t)                                                ;nil
(if-not 42 \t)                                                  ;nil
(if-not nil "unevaluated" \t)                                   ;unevaluated
(if-not false "unevaluated" \t)                                 ;unevaluated
(if-not (not true) \t)                                          ;\t
