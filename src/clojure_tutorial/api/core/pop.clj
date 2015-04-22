(ns clojure-tutorial.api.core.pop)

;pop
;(pop coll)
;For a list or queue, returns a new list/queue without the first item, for a vector, returns a new vector without the last item.
; If the collection is empty, throws an exception.  Note - not the same as next/butlast.

(pop [1 2 3])                                               ;;[1 2]
(peek [1 2 3])                                              ;;3

(pop '(1 2 3))                                              ;;(2 3)
(peek '(1 2 3))                                             ;;1