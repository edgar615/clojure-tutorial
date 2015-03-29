(ns clojure-tutorial.api.core.when-first)

;when-first
;(when-first bindings & body)
;bindings => x xs
;Roughly the same as (when (seq xs) (let [x (first xs)] body)) but xs is evaluated only once

;不懂

(when-first [a [1 2 3]] a)                                  ;;1
(when-first [a []] :x)                                       ;;nil
(when-first [a nil]:x)                                   ;;nil
