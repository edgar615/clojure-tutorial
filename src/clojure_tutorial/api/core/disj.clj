(ns clojure-tutorial.api.core.disj)

;disj
;    (disj set)
; (disj set key)
; (disj set key & ks)

;disj[oin]. Returns a new set of the same (hashed/sorted) type, that does not contain key(s).

(disj #{1 2 3})                                             ;;#{1 3 2}
(disj #{1 2 3} 2)                                           ;;#{1 3}
(disj #{1 2 3} 4)                                           ;;#{1 3 2}
(disj #{1 2 3} 3 1)                                         ;;#{2}

