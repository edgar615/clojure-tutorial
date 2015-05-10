(ns clojure-tutorial.api.core.juxt)

;juxt
;    (juxt f)
; (juxt f g)
; (juxt f g h)
; (juxt f g h & fs)

;Takes a set of functions and returns a fn that is the juxtaposition of those fns.
; The returned fn takes a variable number of args, and returns a vector containing the result of applying each fn to the args (left-to-right).
;((juxt a b c) x) => [(a x) (b x) (c x)]

((juxt :a :b) {:a 1 :b 2 :c 3 :d 4})                        ;;[1 2]