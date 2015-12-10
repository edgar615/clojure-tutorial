;; identical?
;; (identical? x y)
;; Tests if 2 arguments are the same object

(identical? "foot" (str "fo" "ot"))
;; false

(let [a (range 10)]
  (identical? a a))
;; true
