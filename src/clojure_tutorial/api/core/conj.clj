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
(conj #{1 2 3 4} 5)                                         ;;#{1 2 3 4 5}在我电脑的ubuntu环境下是#{1 4 3 2 5}
(conj #{1 3 4} 2)                                         ;#{1 2 3 4}在我电脑的ubuntu环境下是#{1 4 3 2 5}
;nil
(conj nil 3)                                                ;(3)
(conj nil 4 3)                                              ;(3 4)

(= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

;conj操作向量的时候会把元素添加到向量的最后面；操作map的时候会保证把一个键值对添加到map里面；操作set的时候会保证把一个元素添加到集合里面去
;conj会保证对于所有的集合类型，它都会高效地把元素添加进去。
;conj会把元素添加为列表的第一个元素
(conj '(1 2 3) 4)                                           ;;(4 1 2 3)
;如果conj不把元素添加到最前面的话，比如添加到列表的最后面，那么就需要遍历这个列表，这个对于很大的列表来说是很耗时间的操作。
;所以conj保证的并不是它把元素添加到所有集合的最前面或者最后面，它保证的是对于所有的集合它都能高效地插入


;; conj和into的区别
(conj [1] [2])
;; => [1 [2]]
(conj [1] 2)
;; => [0 1]
(into [0] [1])
;; => [0 1]

(defn my-conj
  [target & rest]
  (into target rest))
(my-conj [1] 2 3 4 5)
;; => [1 2 3 4 5]
