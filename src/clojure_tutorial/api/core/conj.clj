(ns clojure-tutorial.api.core.conj)

;conj
;(conj coll x)
;(conj coll x & xs)
;conj[oin]. Returns a new collection with the xs'added'. (conj nil item) returns (item).  The 'addition' may happen at different 'places' depending on the concrete type.
;vector
(conj [2 3 4] 1)                                            ;[2 3 4 1]
;list
(conj '(2 3 4) 1)                                           ;(1 2 3 4)
(conj '(1 2 3) 4)                                           ;(4 1 2 3)
(conj '("a" "b" "c") "d")                                   ;("d" "a" "b" "c")
;conj多个值，是按顺序进行的
;vector
(conj [1 2] 4 3)                                            ;[1 2 4 3]
;list
(conj '(1 2) 4 3)                                           ;(3 4 1 2)
(conj [1 2] [3 4] [5 6])                                    ;[1 2 [3 4] [5 6]]
;map
(conj {:first_name "Edgar" :last_name "Zhang"} {:age 29})   ;{:age 29, :first_name "Edgar", :last_name "Zhang"}
;conjoining to maps only take items as vectors of length exactly 2
(conj {1 2 3 4} 5 6)                                        ;{5 6, 1 2, 3 4}
(conj {1 2, 3 4} [5 6] [7 8])                               ;{7 8, 5 6, 1 2, 3 4}
(conj {:a 1} {:b 2 :c 3} {:d 5 :e 6})                       ;{:d 5, :e 6, :b 2, :c 3, :a 1}
;set
(conj #{1 2 3 4} 5)                                         ;#{1 4 3 2 5}
(conj #{1 3 4} 2)                                         ;#{1 4 3 2 5}
;nil
(conj nil 3)                                                ;(3)
(conj nil 4 3)                                              ;(3 4)

(= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

