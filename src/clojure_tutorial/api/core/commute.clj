(ns clojure-tutorial.api.core.commute)

;commute
;(commute ref fun & args)

;Must be called in a transaction. Sets the in-transaction-value of ref to:
;(apply fun in-transaction-value-of-ref args)
;and returns the in-transaction-value of ref.
;At the commit point of the transaction, sets the value of ref to be:
;(apply fun most-recently-committed-value-of-ref args)
;Thus fun should be commutative, or, failing that, you must accept last-one-in-wins behavior.
;commute allows for more concurrency than ref-set.

(def counter (ref 0))

(defn commute-inc! [counter]
  (dosync (Thread/sleep 100) (commute counter inc)))

(defn alter-inc! [counter]
  (dosync (Thread/sleep 100) (alter counter inc)))

(defn bombard-counter! [n f counter]
  (apply pcalls (repeat n #(f counter))))

(dosync (ref-set counter 0))

(time (doall (bombard-counter! 20 alter-inc! counter)))
;;"Elapsed time: 2013.142698 msecs"
;;(2 1 3 4 8 9 10 5 6 16 7 12 11 14 13 15 19 17 20 18)

(time (doall (bombard-counter! 20 commute-inc! counter)))
;;"Elapsed time: 303.027339 msecs"
;;(6 1 1 1 1 7 5 12 8 13 8 10 11 11 15 18 16 19 16 20)