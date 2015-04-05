(ns clojure-tutorial.api.core.dotimes)

;dotimes
;(dotimes bindings & body)
;bindings => name n
;Repeatedly executes body (presumably for side-effects) with name bound to integers from 0 through n-1.

(dotimes [n 5] (println "n is" n))
;n is 0
;n is 1
;n is 2
;n is 3
;n is 4
;nil
