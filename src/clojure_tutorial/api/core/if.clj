(ns clojure-tutorial.api.core.if)

;if
;Evaluates test.
(if "hi" \t)                                                ;\t
(if 42 \t)                                                  ;\t
(if nil "unevaluated" \t)                                   ;\t
(if false "unevaluated" \t)                                 ;\t
(if (not true) \t)                                          ;nil

;; (if boolean-form
;;   then-form
;;   optional-else-form)

(if true
  "By Zeus's hammer!"
  "By Aquaman's trident!")

(if false
  "By Zeus's hammer!"
  "By Aquaman's trident!")

(if false
  "BY Zeus's hammer!")
;; nil

;; Both nil and false are used to represent logical falsiness, whereas all other values are logically truthy.
;; Truthy and falsey refer to how a value is treated in a Boolean expression,
(if "bears eat beets"
  "bears beets Battlestar Galactica")
;; bears beets Battlestar Galactica

(if nil
  "This won't be the result because nil is falsey"
  "nil is falsey")
;; nil is falsey
