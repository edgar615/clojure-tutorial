(ns clojure-tutorial.api.core.or)

;or
;(or)
;(or x)
;(or x & next)

;Evaluates exprs one at a time, from left to right. If a form returns a logical true value, or returns that value and doesn't
;evaluate any of the other expressions, otherwise it returns the value of the last expression. (or) returns nil.

(or true false false)                                       ;;true
(or true true true)                                         ;;true
(or false true true)                                        ;;true
(or false false false)                                      ;;false
(or nil nil :x)                                             ;;:x
(or true false :x)                                          ;;true
(or false true :x)                                          ;;true
(or false :x true)                                          ;;:x
(or true (println "foo"))                                   ;;true
(or false (println "foo"))                                  ;;nil

(or false nil :large_I_mean_venti :why_cant_I_just_say_large)
;; :large_I_mean_venti

(or (= 0 1) (= "yes" "no"))
;; false

(or nil)
;; nil
