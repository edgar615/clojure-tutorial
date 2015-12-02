;; with-precision
;; (with-precision precision & exprs)

;; Sets the precision and rounding mode to be used for BigDecimal operations.
;;  Usage: (with-precision 10 (/ 1M 3))
;; or:    (with-precision 10 :rounding HALF_DOWN (/ 1M 3))
;;  The rounding mode is one of CEILING, FLOOR, HALF_UP, HALF_DOWN,
;; HALF_EVEN, UP, DOWN and UNNECESSARY; it defaults to HALF_UP.

(with-precision 10 (/ 10M 6))
;; 1.666666667M
(with-precision 10 :rounding FLOOR
  (/ 10M 6))
;; 1.666666666M
