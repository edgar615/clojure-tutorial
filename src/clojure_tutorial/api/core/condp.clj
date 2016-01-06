;; condp
;; (condp pred expr & clauses)

;; Takes a binary predicate, an expression, and a set of clauses.
;; Each clause can take the form of either:
;;  test-expr result-expr
;;  test-expr :>> result-fn
;;  Note :>> is an ordinary keyword.
;;  For each clause, (pred test-expr expr) is evaluated. If it returns
;; logical true, the clause is a match. If a binary clause matches, the
;; result-expr is returned, if a ternary clause matches, its result-fn,
;; which must be a unary function, is called with the result of the
;; predicate as its argument, the result of that call being the return
;; value of condp. A single default expression can follow the clauses,
;; and its value will be returned if no clause matches. If no default
;; expression is provided and no clause matches, an
;; IllegalArgumentException is thrown.

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))
(def warn (partial lousy-logger :warn))
(warn "Hello World")
;; => "hello world"

(condp some [1 2 3 4]
  #{0 6 7} :>> inc
  #{4 5 9} :>> dec
  #{1 2 3} :>> #(+ % 3))
;; 3

(condp some [1 2 3 4]
   #{0 6 7} :>> inc
   #{5 9}   :>> dec)
;; => java.lang.IllegalArgumentException: No matching clause: [1 2 3 4]
