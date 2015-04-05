(ns clojure-tutorial.api.core.next)

;next
;(next coll)
;Returns a seq of the items after the first. Calls seq on its argument.  If there are no more items, returns nil.

(next [:a :b :c])                                           ;;(:b :c)
(next (next [:a :b :c]))                                    ;;(:c)
(next (next (next [:a :b :c])))                             ;;nil
