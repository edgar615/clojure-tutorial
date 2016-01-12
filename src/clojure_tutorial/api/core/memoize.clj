;; memoize
;; (memoize f)

;; Returns a memoized version of a referentially transparent function. The
;; memoized version of the function keeps a cache of the mapping from arguments
;; to results and, when calls with the same arguments are repeated often, has
;; higher performance at the expense of higher memory use.

(defn sleepy-identity
  "Returns the given value after 1 second"
  [x]
  (Thread/sleep 1000)
  x)

(time (sleepy-identity "Mr. Fantastico"))
;; "Elapsed time: 1000.298625 msecs"
;; => "Mr. Fantastico"


(time (sleepy-identity "Mr. Fantastico"))
;; "Elapsed time: 1000.298625 msecs"
;; => "Mr. Fantastico"

(def memo-sleepy-identity (memoize sleepy-identity))
(time (memo-sleepy-identity "Mr. Fantastico"))
;; "Elapsed time: 1000.298625 msecs"
;; => "Mr. Fantastico"

(time (memo-sleepy-identity "Mr. Fantastico"))
;; "Elapsed time: 0.121666 msecs"
;; => "Mr. Fantastico"
