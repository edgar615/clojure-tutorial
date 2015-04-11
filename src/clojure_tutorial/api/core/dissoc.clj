(ns clojure-tutorial.api.core.dissoc)

;dissoc
;    (dissoc map)
; (dissoc map key)
; (dissoc map key & ks)

;dissoc[iate]. Returns a new map of the same (hashed/sorted) type,that does not contain a mapping for key(s).

(dissoc {:a 1 :b 2 :c 3})                                   ;;{:c 3, :b 2, :a 1}
(dissoc {:a 1 :b 2 :c 3} :b)                                ;;{:c 3, :a 1}
(dissoc {:a 1 :b 2 :c 3} :d)                                ;;{:c 3, :b 2, :a 1}
(dissoc {:a 1 :b 2 :c 3} :b :c)                             ;;{:a 1}