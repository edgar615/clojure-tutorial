(ns clojure-tutorial.api.core.false?)

;false?
;(false? x)
;Returns true if x is the value false, false otherwise.
(false? true)                                                ;;false
(false? false)                                               ;;true
(false? nil)                                                 ;;false
(false? :x)                                                  ;;false
(false? (not (= 1 1)))                                             ;;true
