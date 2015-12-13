;; do


;; Evaluates the expressions in order and returns the value of the last. If no
;; expressions are supplied, returns nil. See http://clojure.org/special_forms
;; for more information.

;; The do operator lets you wrap up multiple forms in parentheses and run each of them.

(if true
  (do (print "Successed"
             "By Zeus's hammer!"))
  (do (print "Failed"
             "By Aquaman's trident!")))

(do
  (println "LOG: Computing...")
  (+ 1 1))
;; 2
