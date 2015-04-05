(ns clojure-tutorial.api.core.rest)

;rest
;(rest coll)
;Returns a possibly empty seq of the items after the first. Calls seq on its argument.

(rest [1 2 3 4 5])                                          ;;(2 3 4 5)
(rest '())                                                  ;;()
(rest nil)                                                  ;;()