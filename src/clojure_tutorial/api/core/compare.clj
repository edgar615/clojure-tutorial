(ns clojure-tutorial.api.core.compare)

;compare
;(compare x y)

;Comparator. Returns a negative number, zero, or a positive number when x is logically 'less than', 'equal to', or 'greater than' y.
; Same as Java x.compareTo(y) except it also works for nil, and compares numbers and collections in a type-independent manner. x must implement Comparable

(compare 2 2)                                               ;;0
(compare "ab" "abc")                                        ;;-1
(compare ["a" "b" "c"] ["a" "b"])                           ;;1

(compare [0 1 2] [0 1 2])                                   ;;0

(compare [1 2 3] [0 1 2 3])                                 ;;-1
(compare [0 1 2] [3 4])                                     ;;1
(compare nil [1 2 3])                                       ;;-1
(compare [1 2 3] nil)                                       ;;1
(compare "abc" "def")                                       ;;-3
(compare "abc" "abd")                                       ;;-1

