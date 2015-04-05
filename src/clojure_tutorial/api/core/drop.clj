(ns clojure-tutorial.api.core.drop)

;drop
;(drop n coll)
;Returns a lazy sequence of all but the first n items in coll.

;; although negative (or zero) drop-item-counts are accepted they do nothing
(drop -1 [1 2 3 4])                                         ;;(1 2 3 4)
(drop 0 [1 2 3 4])                                          ;;(1 2 3 4)
(drop 1 [1 2 3 4])                                          ;;(2 3 4)
(drop 2 [1 2 3 4])                                          ;;(3 4)
(drop 5 [1 2 3 4])                                          ;;()

