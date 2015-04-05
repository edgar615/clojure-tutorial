(ns clojure-tutorial.api.core.first)

;first
;(first coll)
;Returns the first item in the collection. Calls seq on its argument. If coll is nil, returns nil.

(first "Clojure")                                           ;;\C

(first '(:a :b :c))                                         ;;:a

(first nil)                                                 ;;nil
(first [])                                                  ;;nil