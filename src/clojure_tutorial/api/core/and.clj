(ns clojure-tutorial.api.core.and)

;and
;(and)
;(and x)
;(and x & next)
;Evaluates exprs one at a time, from left to right. If a form returns logical false (nil or false), and returns that value and
;doesn't evaluate any of the other expressions, otherwise it returns the value of the last expr. (and) returns true.

(and true true)                                             ;;true
(and true false)                                            ;;false
(and false false)                                           ;;false
(and true :x)                                               ;;:x
(and false :x)                                              ;;false
(and '() :x)                                                ;;:x
(and 0 1)                                                   ;;1
(and 1 0)                                                   ;;0

(and :free_wifi :hot_coffee)
;; :hot_coffee

(and :feelin_super_cool nil false)
;; nil
