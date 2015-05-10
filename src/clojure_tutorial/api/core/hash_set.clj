(ns clojure-tutorial.api.core.hash-set)

;hash-set
;    (hash-set)
; (hash-set & keys)
;Returns a new hash set with supplied keys.  Any equal keys are handled as if by repeated uses of conj.

(hash-set :a :b :c :d)                                      ;;#{:c :b :d :a}
(hash-set 1 2 1 3 1 4 1 5)                                  ;;#{1 4 3 2 5}

