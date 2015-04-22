(ns clojure-tutorial.api.core.nth)
;nth
;    (nth coll index)
; (nth coll index not-found)

;Returns the value at the index. get returns nil if index out of bounds, nth throws an exception unless not-found is supplied.
; nth also works for strings, Java arrays, regex Matchers and Lists, and, in O(n) time, for sequences.


(nth [:a :b :c] 2)                                          ;;:c

(nth [:a :b :c] 3)                                          ;;IndexOutOfBoundsException

(nth [:a :b :c] -1 "not-found")                             ;;"not-found"

(nth 42 0)                                                  ;;UnsupportedOperationException nth not supported on this type: Long
