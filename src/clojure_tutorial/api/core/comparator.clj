(ns clojure-tutorial.api.core.comparator)

;comparator
;(comparator pred)
;Returns an implementation of java.util.Comparator based upon pred.

((comparator <) 1 4)                                        ;;-1
((comparator <) 4 1)                                        ;;1
((comparator <) 4 4)                                        ;;0

(def a (java.util.ArrayList. [1 2 0]))
(def compx (comparator #(> % %2)))
(java.util.Collections/sort a compx)
a                                                           ;;[2 1 0]