(ns clojure-tutorial.api.core.merge)

;merge
;(merge & maps)
; Returns a map that consists of the rest of the maps conj-ed onto the first.
; If a key occurs in more than one map, the mapping from the latter (left-to-right) will be the mapping in the result.

(merge {:a 1 :b 2 :c 3} {:b 8 :d 9})
;;{:d 9, :c 3, :b 8, :a 1}