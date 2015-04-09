(ns clojure-tutorial.api.core.split-with)

;split-with
;(split-with pred coll)
;Returns a vector of [(take-while pred coll) (drop-while pred coll)]

(split-with (partial > 3) [1 2 3 4 5])                      ;;[(1 2) (3 4 5)]
(split-with (partial >= 3) [1 2 3 4 5])                     ;;[(1 2 3) (4 5)]
(split-with (partial > 3) [1 2 3 2 1])                      ;;[(1 2) (3 2 1)]