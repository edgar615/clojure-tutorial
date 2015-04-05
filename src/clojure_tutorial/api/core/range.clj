(ns clojure-tutorial.api.core.range)

;range
;    (range)(range end)(range start end)(range start end step)
;Returns a lazy seq of nums from start (inclusive) to end (exclusive), by step, where start defaults to 0, step to 1, and end to infinity.
; When step is equal to 0, returns an infinite sequence of start. When start is equal to end, returns empty list.

;; default value of 'end' is infinity
(range)                                                     ;;

(take 10 (range))                                                  ;;(0 1 2 3 4 5 6 7 8 9)
(range 10)                                                  ;;(0 1 2 3 4 5 6 7 8 9)
(range -5 5)                                                ;;(-5 -4 -3 -2 -1 0 1 2 3 4)
(range -100 100 10)                                         ;;(-100 -90 -80 -70 -60 -50 -40 -30 -20 -10 0 10 20 30 40 50 60 70 80 90)
(range 100 0 -10)                                           ;;(100 90 80 70 60 50 40 30 20 10)
(range 10 -10 -1)                                           ;;(10 9 8 7 6 5 4 3 2 1 0 -1 -2 -3 -4 -5 -6 -7 -8 -9)