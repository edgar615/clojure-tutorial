(ns clojure-tutorial.coll.sorted)

;sorted
;实现sorted抽象的集合保证集合内部的值被以特定的顺序保存，这个顺序可以通过一个谓词活摘一个特定的comparator接口来定义。
;这使得你可以高效地、正序（或者反序）获取集合或者集合的一部分。这个接口包含以下函数：
;rseq函数可以在常量时间内反序返回一个集合的元素
;subseq可返回一个集合的某一个区间的元素的序列
;rsubseq跟subseq类似，但是返回的元素是反序的

;只有map和set实现了sorted接口。没有字面量来表示sorted的集合。要创建sorted集合可以用sorted-map和sorted-set来创建有序的map和set.
;如果要提供你自己的谓词或比较器来定义排序规则的话，使用sorted-map-by和sorted-set-by

(def sm (sorted-map :z 5 :x 9 :y 0 :b 2 :a 3 :z 5))
sm                                                          ;;{:a 3, :b 2, :x 9, :y 0, :z 5}
(rseq sm)                                                   ;;([:z 5] [:y 0] [:x 9] [:b 2] [:a 3])
(subseq sm > :b <= :y)                                      ;;([:x 9] [:y 0])
(rsubseq sm > :b <= :y)                                     ;;([:y 0] [:x 9])

;compare函数定义默认排序：正序，它支持所有的Clojure标量以及顺序集合。它会按照字典排序法来在每一层对元素进行排序
(compare 2 2)                                               ;;0
(compare "ab" "abc")                                        ;;-1
(compare ["a" "b" "c"] ["a" "b"])                           ;;1
;实际上compare不止支持字符串、数字以及顺序集合。它支持任何实现了java.lang.Comparable接口的值，包括布尔值、关键字、符号以及所有实现这个接口的java类

;Clojure里面所有的函数都实现了java.util.Comparator接口，所以它们抖森潜在的比较器。不过并不是所有的函数抖森设计来作为比较器的。你不需要一定实现Comparator接口才能实现一个比较器——任何一个两参数的谓词都可以。

;不用实现一个特定的接口就能定义一个比较器也意味着我们可以很容易地定义多层排序:先按照一个规则排序，在这个基础上再按照另外一个规则排序。只要定义一个高阶函数旧可以了。
;比较函数可以直接传给有序集合的工厂函数，如sort和sort-by

(sort < (repeatedly 10 #(rand-int 100)))                    ;;(3 6 8 19 37 61 68 74 83 89)

;comparator
;sorted-map-by
;sorted-set-by

;访问集合元素的简洁方式

