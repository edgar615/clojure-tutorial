(ns clojure-tutorial.api.core.list?)

;list?
;(list? x)
;Returns true if x implements IPersistentList

(list? '(1 2 3))                                            ;;true
(list? [])                                                  ;;false