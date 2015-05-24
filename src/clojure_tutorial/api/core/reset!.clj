(ns clojure-tutorial.api.core.reset!)

;reset!
;(reset! atom newval)
;Sets the value of atom to newval without regard for the current value. Returns newval.

(def x (atom 10))

@x                                                          ;;10
(reset! x 20)                                               ;;20
@x                                                          ;;20