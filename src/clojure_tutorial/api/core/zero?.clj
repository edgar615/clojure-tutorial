(ns clojure-tutorial.api.core.zero?)

;zero?
;(zero? x)
;Returns true if num is zero, else false

(zero? 0)               ;;=> true
(zero? 0.0)             ;;=> true
(zero? 2r000)           ;;=> true
(zero? 0x0)             ;;=> true

(zero? 1)               ;;=> false
(zero? 3.14159265358M)  ;;=> false
(zero? (/ 1 2))         ;;=> false

(zero? nil)             ;;=> NullPointerException