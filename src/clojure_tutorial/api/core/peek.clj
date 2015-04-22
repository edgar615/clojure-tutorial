(ns clojure-tutorial.api.core.peek)

;peek
;(peek coll)
;For a list or queue, same as first, for a vector, same as, but much more efficient than, last. If the collection is empty, returns nil.

(pop [1 2 3])                                               ;;[1 2]
(peek [1 2 3])                                              ;;3

(pop '(1 2 3))                                              ;;(2 3)
(peek '(1 2 3))                                             ;;1

(def large-vec (vec (range 0 10000)))
(time (last large-vec))
;"Elapsed time: 2.299108 msecs"
;9999

(time (peek large-vec))
;"Elapsed time: 0.047124 msecs"
;9999
