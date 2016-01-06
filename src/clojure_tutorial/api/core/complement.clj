;; complement
;; (complement f)

;; Takes a fn f and returns a fn that takes the same arguments as f,
;; has the same effects, if any, and returns the opposite truth value.

(def not-empty? (complement empty?))
(not-empty? "a")
;; => true
(not-empty? [])
;;=> false
(not-empty? [1 2])
;; => true
