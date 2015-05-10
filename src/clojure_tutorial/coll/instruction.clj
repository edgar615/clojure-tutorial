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

;数据解构类型
;List
;Clojure的列表是单向链表，因此支队链头支持高效的访问和“修改”操作——使用conj把一个新元素加到列表上去，使用pop或者序列操作符rest来获取部包含链头的子列表
;因为是链表，所以它不支持高效的随机访问操作；所以队一个列表调用nth的时间开销是线性的而不是常量，而且列表根本就不支持get，因为在列表上无法达到get亚线性的性能要求。
;列表是它们自身的序列，所以对一个列表调用seq返回这个列表本身而不是对这个列表的顺序视图

;list函数接受任意数量的参数，而每一个参数会成为返回的列表的一个元素。
;list?可以用来测试一个集合是不是列表


;Vector
;vector是一种顺序数据结构，它支持高效的随机访问和更改语义，跟java的ArrayList类似。它实现了associative、indexed以及stack抽象

;vector创建vector
;vec只接受一个参数，它可以把这个传入的集合转换成一个新的vector。
;vector?函数用来测试一个值是不是vector

;vector当做元组
;元组(tuple)是vector的最常见的使用场景。任何时候你想把多个值绑定在一起处理——比如你想从一个函数中返回多个值——那时候你旧可以把这多个值放进一个vector

(defn euchidian-division
  [x y]
  [(quot x y) (rem x y)])
(euchidian-division 42 8)                                   ;;[5 2]
;等同于
((juxt quot rem) 42 8)                                      ;;[5 2]

(let [[q r] (euchidian-division 53 7)]
  (str "53/7 = " q " * 7 + " r))                            ;;"53/7 = 7 * 7 + 4"

;set
#{1 2 3}                                                    ;;
#{1 2 3 3}                                                  ;;IllegalArgumentException Duplicate key: 3
;hash-set
;set

;map
{:a 5 :b 6}
{:a 5 :a 6}                                                 ;;IllegalArgumentException Duplicate key: :a
;hash-map
;keys
;vals
;map经常被当作一种简单灵活的数据模型来使用


;不可变性和持久性


;易变集合
;持久化集合保证一个值的历史版本的完整性，而易变集合不做这个保证
;易变集合是可修改的
(def x (transient []))
(def y (conj! x 1))
(count y)                                                   ;;1
(count x)                                                   ;;1

;持久性数据结构所保证的值语义对于没一个更新操作都需要进行内存分配，而这个内存分配确实会有一些开销。虽然对于单个操作来说这个开销可以忽略，但是如果进行大量的这种操作，比如要把成千上万个值用conj添加到一个集合中去，这些开销经过累积之后就相当可观了。

;易变集合只是设计来对这种场景进行优化的。在有些场景下，易变集合可以减少甚至消除这种累积的对象分配，因此也就最小化了垃圾收集时间，从而也就降低了整体的集合构造时间。
;它对于那种在一个循环中不断创建对象是一种很方便的优化手段。

(defn naive-into
  [coll source]
  (reduce conj coll source))

(= (into #{} (range 5)) (naive-into #{} (range 5)))         ;;true
(time (do (into #{} (range 1e6)) nil))                      ;;"Elapsed time: 715.343258 msecs"
(time (do (naive-into #{} (range 1e6)) nil))                ;;"Elapsed time: 1879.915188 msecs"
;优化后
(defn fast-into
  [coll source]
  (persistent! (reduce conj! (transient coll) source)))
(time (do (fast-into #{} (range 1e6)) nil))                 ;;"Elapsed time: 732.043314 msecs"

;persistent!把一个易变集合变成持久性集合后会使那个易变集合不再可用

;易变集合的更新函数: conj! assoc! dissoc! disj! pop!
;一旦在易变集合上使用了上面的任意函数，那个集合就再也不可用了，即使是读取操作也不行。
(let [tm (transient {})]
  (doseq [x (range 100)]
    (assoc! tm x 0))
  (persistent! tm))                                         ;;{0 0, 1 0, 2 0, 3 0, 4 0, 5 0, 6 0, 7 0}
;上述代码集合所有的记录都丢了，因为这里没有保留assoc!的返回值


;易变集合只能在创建这个集合的线程上内操作。

(let [t (transient {})]
  @(future (get t :a)))                                     ;;IllegalAccessError Transient used by non-owner thread

;易变集合不能组合。persistent!不会遍历你创建的嵌套易变集合
(persistent! (transient [(transient {})]))
;;[#<TransientArrayMap clojure.lang.PersistentArrayMap$TransientArrayMap@5c11ac2d>]
;在任何情况下，易变集合是可修改的，它们没有值的语义，因此不能这样比较：
(= (transient [1 2]) (transient [1 2]))                     ;;false
;易变集合不应该和值语义混起来使用，它应该只作为一个本地的优化策略，不应该暴露给用户


;元数据
;元数据可以被关联到任何Clojure数据结构、序列、记录、符号以及引用类型，而且始终是以一个ｍａｐ的形式。
;Clojure还提供了一个ｒｅａｄｅｒ字面量来更方便地把元数据关联到值：
(def a ^{:created (System/currentTimeMillis)}
  [1 2 3])
(meta a)                                                    ;;{:created 1431247211121}

;如果要指定的元数据的key是关键字，而值是布尔值，可以使用一种简单的方式来指定
(meta ^:private [1 2 3])                                    ;;{:private true}
(meta ^:private ^:dynamic [1 2 3])                          ;;{:private true, :dynamic true}

;更新一个给定值的元数据：with-meta、vary-meta
;with-meta 把一个元数据完全替换成给定的元数据
(def b (with-meta a (assoc (meta a)
                      :modified (System/currentTimeMillis))))
(meta b)                                                    ;;{:modified 1431248005353, :created 1431247211121}

;vary-meta通过给定的更新函数以及需要的参数对值当前的元数据进行更新
(def b (vary-meta a assoc :modified (System/currentTimeMillis)))
(meta b)                                                    ;;{:modified 1431248068865, :created 1431247211121}

;元数据是关于数据的数据，你改变一个值的元数据并不会改变这个值出来的形式，也不会影响这个值和别的值的相等性
(= a b)                                                     ;;true
(= ^{:a 5} 'any-value
   ^{:b 5} 'any-value)                                      ;;true

;一个有元数据的值跟没有元数据的值一样是不可变的，所以对数据结构近线修改的操作返回的新的数据结构会保留原值的元数据
(meta (conj a 500))                                         ;;{:created 1431247211121}