(ns clojure-tutorial.api.core.if)

;if
;Evaluates test.
(if "hi" \t)                                                ;\t
(if 42 \t)                                                  ;\t
(if nil "unevaluated" \t)                                   ;\t
(if false "unevaluated" \t)                                 ;\t
(if (not true) \t)                                          ;nil