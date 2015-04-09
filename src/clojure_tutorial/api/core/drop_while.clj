(ns clojure-tutorial.api.core.drop-while)

;drop-while
;(drop-while pred coll)
;Returns a lazy sequence of the items in coll starting from the first item for which (pred item) returns logical false.

(drop-while neg? [-1 -2 -6 -7 1 2 3 4 -5 -6 0 1])           ;;(1 2 3 4 -5 -6 0 1)
