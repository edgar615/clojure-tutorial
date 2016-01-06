;identity
;(identity x)
;Returns its argument

(identity 4)
;; => 4

(filter identity [1 2 3 nil 4 false true 1234])             ;;(1 2 3 4 true 1234)
