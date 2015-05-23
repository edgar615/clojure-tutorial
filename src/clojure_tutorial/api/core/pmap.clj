(ns clojure-tutorial.api.core.pmap)

;pmap
;    (pmap f coll)(pmap f coll & colls)
;Like map, except f is applied in parallel. Semi-lazy in that the parallel computation stays ahead of the consumption, but doesn't
;realize the entire result unless required. Only useful for computationally intensive functions where the time of f dominates the coordination overhead.

(pmap inc [1 2 3 4 5])                                      ;;(2 3 4 5 6)

(defn long-running-job [n]
  (Thread/sleep 3000)
  (+ n 10))
(time (dorun (map long-running-job (range 4))))
;;"Elapsed time: 12000.760349 msecs"
;;
(time (dorun (pmap long-running-job (range 4))))
;;"Elapsed time: 3001.06874 msecs"
;;