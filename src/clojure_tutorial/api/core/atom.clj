(ns clojure-tutorial.api.core.atom)

;atom
;    (atom x)(atom x & options)

;Creates and returns an Atom with an initial value of x and zero or more options (in any order):
;:meta metadata-map
;:validator validate-fn
;If metadata-map is supplied, it will become the metadata on the atom.
; validate-fn must be nil or a side-effect-free fn of one argument, which will be passed the intended new state on any state change.
; If the new state is unacceptable, the validate-fn should return false or throw an exception.

(def my-atom (atom 0))

@my-atom                                                    ;;0

(swap! my-atom inc)                                         ;;1

@my-atom                                                    ;;1

(swap! my-atom (fn [n] (* (+ n n) 2)))                      ;;4