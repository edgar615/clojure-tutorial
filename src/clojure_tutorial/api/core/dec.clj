(ns clojure-tutorial.api.core.dec)

;(dec x)
;Returns a number one less than num. Does not auto-promote longs, will throw on overflow. See also: dec'

(dec 2)                                                     ;1
(dec 2.0)                                                   ;1.0
(dec 1)                                                     ;0
(dec -1)                                                    ;-2