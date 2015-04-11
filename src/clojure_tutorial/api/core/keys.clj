(ns clojure-tutorial.api.core.keys)

;keys
;(keys map)
;Returns a sequence of the map's keys, in the same order as (seq map).

(keys {:a 1 :b 2})                                          ;;(:b :a)
(keys nil)                                                  ;;nil
(keys {})                                                   ;;nil
