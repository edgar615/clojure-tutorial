(ns clojure-tutorial.api.core.string?)

;string?
;(string? x)

;Return true if x is a String

(string? "abc")                                             ;;true
(string? 1)                                                 ;;false
(string? \a)                                                ;;false
(string? nil)                                               ;;false