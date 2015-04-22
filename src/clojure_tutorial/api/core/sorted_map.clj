(ns clojure-tutorial.api.core.sorted-map)

;sorted-map
;(sorted-map & keyvals)

;keyval => key val
;Returns a new sorted map with supplied mappings.  If any keys are equal, they are handled as if by repeated uses of assoc.

(sorted-map :z 1 :b 2 :c 3)                                 ;;{:b 2, :c 3, :z 1}

