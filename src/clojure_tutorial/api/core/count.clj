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

;count保证对所有集合操作耗时抖高效的（序列除外，因为序列的长度可以是未知的）