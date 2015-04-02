(ns clojure-tutorial.coll.instruction)

'(a b :name 12.5)                                           ;;列表
['a 'b :name 12.5]                                          ;;vector
{:name "Chas" :age 31}                                      ;;map
{Math/PI "-3.14"
 [:composite "key"] 42
 nil "nothing"}                                             ;;map

#{{:first-name "chas" :last-name "emerick"}
  {:first-name "brian" :last-name "carper"}
  {:first-name "christophe" :last-name "grand"}}            ;;包含set的map

;Clojure的数据结构有两个特色
;1.数据结构首先是依据抽象来用的，而不是依据具体的实现细节来用。
;2.数据结构是不可改变的而且是持久的。

;抽象优于实现
;100个函数操作1种数据结构比10个函数操作10种数据结构要好

(def v [1 2 3])
(conj v 4)                                                  ;;[1 2 3 4]
(conj v 4 5)                                                ;;[1 2 3 4 5]
(seq v)                                                     ;;(1 2 3)

;同样的操作可以用于映射
(def m {:a 5 :b 6})
(conj m {:c 7})                                             ;;{:c 7, :b 6, :a 5}
(seq m)                                                     ;;([:b 6] [:a 5])

;也可以用于set
(def s #{1 2 3})
(conj s 10)                                                 ;;#{1 3 2 10}

(conj s 5 3 4)                                              ;;#{1 4 3 2 5}
(seq s)                                                     ;;(1 3 2)

;也可用于列表
(def l '(1 2 3))
(conj l 0)                                                  ;;(0 1 2 3)
(conj l 0 -1)                                               ;;(-1 0 1 2 3)
(seq l)                                                     ;;(1 2 3)

;into是建立在seq和conj之上的，意味着into能自动用于任何支持seq和conj的值
(into v [4 5])                                              ;;[1 2 3 4 5]
(into m [[:c 7] [:d 8]])                                    ;;{:c 7, :b 6, :d 8, :a 5}
(into #{1 2} [2 3 4 5 3 3 2])                               ;;#{1 4 3 2 5}
(into [1] {:a 1 :b 2})                                      ;;[1 [:b 2] [:a 1]]

;构建小而广泛支持的抽象是Clojure的核心设计原则。很多Clojure的数据结果都只支持某个接口的一个小部分。
;下面是Clojure集合中会实现的几个主要抽象：
;Collection
;Sequence
;Associative
;INdexed
;Stack
;Set
;Sorted