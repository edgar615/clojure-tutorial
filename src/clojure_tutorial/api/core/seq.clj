(ns clojure-tutorial.api.core.seq)

;seq
;(seq coll)
;Returns a seq on the collection. If the collection is empty, returns nil.  (seq nil) returns nil.
; seq also works on Strings, native Java arrays (of reference types) and any objects that implement Iterable.

(seq '(1))                                                  ;;(1)
(seq [1 2])                                                 ;;(1 2)
(seq "abc")                                                 ;;(\a \b \c)

(seq nil)                                                   ;;nil
(seq '())                                                   ;;nil
(seq [])                                                    ;;nil

(seq {:a 5 :b 6})                                           ;;([:b 6] [:a 5])
(seq (java.util.ArrayList. (range 5)))                      ;;(0 1 2 3 4)
(seq (into-array ["clojure" "tutorial"]))                   ;;("clojure" "tutorial")

(seq '(1 2 3 ))
;; => (1 2 3)

(seq [1 2 3])
;; => (1 2 3)

(seq #{1 2 3})
;; => (1 2 3)

(seq {:name "Bill Compton" :occupation "Dead mopey guy"})
;; ([:name "Bill Compton"] [:occupation "Dead mopey guy"])
