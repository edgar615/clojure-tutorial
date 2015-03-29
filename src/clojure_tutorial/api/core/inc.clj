(ns clojure-tutorial.api.core.inc)

;inc
;(inc x)
;Returns a number one greater than num. Does not auto-promote longs, will throw on overflow. See also: inc'

(inc 1)                                                     ;;2
(inc 1.0)                                                   ;;2.0
(inc 1/2)                                                   ;;3/2
(inc 1/3)                                                   ;;4/3
(inc -1)                                                    ;;0