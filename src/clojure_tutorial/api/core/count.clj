(ns clojure-tutorial.api.core.count)

;count
;(count coll)

;Returns the number of items in the collection. (count nil) returns 0.
; Also works on strings, arrays, and Java Collections and Maps

(count nil)                                                 ;;0
(count [])                                                  ;;0
(count [1 2 3])                                             ;;3
(count '(1 2 3))                                            ;;3
(count {:a 1 :b 2})                                         ;;2
(count "abcd")                                              ;;4