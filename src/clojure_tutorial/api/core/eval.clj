;; eval
;; (eval form)

;; Evaluates the form data structure (not text!) and returns the result.

(def *foo* "(println [1 2 3])")
*foo*
;; => "(println [1 2 3])"
(eval *foo*)
;; => "(println [1 2 3])"

(eval (read-string *foo*))

(eval '(let [a 10] (+ 3 4 a)))
;; => 17

(def addition-list (list + 1 2))
(eval addition-list)
;; => 3

(eval (concat addition-list [10]))
;; => 13

(eval (list 'def 'lucky-number (concat addition-list [10])))
lucky-number
;; => 13
