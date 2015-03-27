(ns clojure-tutorial.api.core.loop)

;loop
;(loop [bindings*] exprs*)
;Evaluates the exprs in a lexical context in which the symbols in the binding-forms are bound to their respective init-exprs or parts therein. Acts as a recur target.

(loop [x 10]
  (when (> x 1)
    (println x)
    (recur (- x 2))))
