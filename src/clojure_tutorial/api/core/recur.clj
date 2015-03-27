(ns clojure-tutorial.api.core.recur)

;recur
;Evaluates the exprs in order, then, in parallel, rebinds the bindings of the recursion point to the values of the exprs.

(loop [i 0]
  (when (< i 5)
    (println i)
    (recur (inc i))))                                       ;