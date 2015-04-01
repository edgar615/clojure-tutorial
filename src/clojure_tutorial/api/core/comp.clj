(ns clojure-tutorial.api.core.comp)

;comp
;    (comp)
;(comp f)
;(comp f g)
;(comp f g h)
;(comp f1 f2 f3 & fs)

;Takes a set of functions and returns a fn that is the composition of those fns.  The returned fn takes a variable number of args,
;applies the rightmost of fns to the args, the next fn (right-to-left) to the result, etc.

((comp str - +) 1 2 3 4 5.6)                                ;;"-15.6"

(def negative-quotient (comp - /))
(negative-quotient 8 3)                                     ;;-8/3

(def concat-and-reverse (comp (partial apply str) reverse str))
(concat-and-reverse "hello" "clojuredocs")                  ;;"scoderujolcolleh"
