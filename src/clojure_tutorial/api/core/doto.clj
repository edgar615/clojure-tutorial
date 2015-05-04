(ns clojure-tutorial.api.core.doto)
;doto
;(doto x & forms)
;Evaluates x then calls all of the methods and functions with the value of x supplied at the front of the given arguments.
; The forms are evaluated in order.  Returns x.
;(doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2))

(doto (java.util.HashMap.)
  (.put "a" 1)
  (.put "b" 2))                                             ;;{"a" 1, "b" 2}