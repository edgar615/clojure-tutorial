;; areduce
;; (areduce a idx ret init expr)

;; Reduces an expression across an array a, using an index named idx,
;; and return value named ret, initialized to init, setting ret to the
;; evaluation of expr at each step, returning ret.

(let [a (int-array (range 10))]
  (areduce a i sum 0
           (+ sum (aget a i))))
;; 45
