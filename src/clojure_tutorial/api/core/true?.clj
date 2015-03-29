(ns clojure-tutorial.api.core.true?)


;true?
;(true? x)
;Returns true if x is the value true, false otherwise.
(true? true)                                                ;;true
(true? false)                                               ;;false
(true? nil)                                                 ;;false
(true? :x)                                                  ;;false
(true? (= 1 1))                                             ;;true