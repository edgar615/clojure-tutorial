(ns clojure-tutorial.api.core.repeat)

;repeat
;    (repeat x)(repeat n x)
;Returns a lazy (infinite!, or length n if supplied) sequence of xs.

(take 5 (repeat "x"))                                       ;;("x" "x" "x" "x" "x")
(repeat 5 "x")                                              ;;("x" "x" "x" "x" "x")