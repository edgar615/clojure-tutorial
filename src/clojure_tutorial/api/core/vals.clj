(ns clojure-tutorial.api.core.vals)

;vals
;(vals map)
;Returns a sequence of the map's values, in the same order as (seq map).

(vals {:a 1 :b 2})                                          ;;(2 1)
(vals nil)                                                  ;;nil
(vals {})                                                   ;;nil