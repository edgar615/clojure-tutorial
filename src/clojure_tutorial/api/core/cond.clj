(ns clojure-tutorial.api.core.cond)

;cond
;(cond & clauses)
;Takes a set of test/expr pairs. It evaluates each test one at a time.  If a test returns logical true, cond evaluates and returns
;the value of the corresponding expr and doesn't evaluate any of the other tests or exprs. (cond) returns nil.

(let [rnd (rand-int 100)]
  (cond
    (>= rnd 90) "A"
    (>= rnd 80) "B"
    (>= rnd 70) "C"
    (>= rnd 60) "D"
    :default "E"))