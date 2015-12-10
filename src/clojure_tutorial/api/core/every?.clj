;; every?
;; (every? pred coll)
;; Returns true if (pred x) is logical true for every x in coll, else false.

(every? even? `(2 4 6))
;; true
(every? even? `(1 2 3))
;; false
