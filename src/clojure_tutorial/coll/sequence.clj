(ns clojure-tutorial.coll.sequence)

;Sequence接口定义了一个获取并且遍历各种集合的一个顺序视图的一种方法。这个集合可以是另一种集合，也可以是某个函数的返回值。
;除了Collection提供的函数，seq还提供一些额外的接口

;seq函数返回给传入参数的一个序列
;first、rest以及next提供了遍历序列的一个方法
;lazy-seq创建一个内容是一个表达式结果的惰性序列

;那些可以用seq来产生有效值的类型成为可序列的类型
;所有的Clojure集合类型
;所有的java集合类型
;所有的java map
;所有的java.lang.CharSequence，包括String
;实现了java.lang.Iterable的任意类型
;数组
;nil，或者是java方法返回的null
;任何实现了clojure.Seqable接口的类型


;序列不是迭代器

;序列不是列表
;第一眼看上去，序列跟列表很像：序列要么是空的，要么包含两个部分，一个作为头的值以及一个本身是序列的尾巴，同时列表本身就是自身的序列。
;但是它们在很多重要的方面都有不同
;1.要计算一个序列的长度是比较耗时的
;2.序列的内容可能是惰性的，而只有在真的要用到序列中值的时候才会去实例化
;3.一个生成序列的函数可能生成一个无限惰性序列，所以也就是不可数的
;作为对比，列表会保存它的长度，所以要获取列表长度的方法是非常高效的。而序列不能保证这一点，因为这个序列可能是惰性序列，甚至可能是无限序列。所以获取序列长度的唯一方法是去遍历这个序列
(let [s (range 1e6)]
  (time (count s)))
;"Elapsed time: 150.600124 msecs"
;1000000
(let [s (apply list (range 1e6))]
  (time (count s)))
;"Elapsed time: 0.032553 msecs"
;1000000

;创建序列
;序列之上其他集合的一个顺序视图，一般来说，一个序列是从一个集合生成而来，要么是通过seq函数直接创建，要么通过其他函数间接调用seq

;直接创建序列
;cons
;list*

;惰性序列
;在序列中，一个集合的具体内容可以是惰性生成的，这种情况下集合的元素是一个函数调用的结果，而我们只在真正需要集合里面元素内容的时候才去调用函数去计算一次，而且只计算一次。
;访问一个惰性序列的过程被称为实例化，当一个惰性序列中的元素都被计算出来了，我们说这个序列被完全实例化了。

;可以通过lazy-seq来创建一个惰性序列，这个宏接受任意返回值是一个可序列的值的表达式。

;rest和next的区别
;next始终返回nil而不是空的序列，它之所以能够做到这一点是因为它始终会去强制实例化序列尾巴的第一个元素
(defn random-ints
  [limit]
  (lazy-seq
    (println "realizing random number")
    (cons (rand-int limit)
          (random-ints limit))))
(def x (next (random-ints 50)))
;realizing random number
;realizing random number
;#'user/x

;rest始终简单地返回序列的尾巴，从而也就避免了去实例化序列的尾巴第一个元素，也就是最大程度的“惰性”
(def x (rest (random-ints 50)))
;realizing random number
;#'user/x

;顺序解构始终使用next而不是rest，所以如果你在解构一个惰性序列的话，那么始终会实例化它的尾巴的头元素
(let [[x & rest] (random-ints 50)])
;realizing random number
;realizing random number
;nil

;而在另外一些情况下，需要完全实例化一个惰性序列。如果你想要保持这个序列的所有元素，那么应该使用doall。
;如果并不需要惰性序列的具体内容（比如你想要的是惰性序列实例化的时候所产生的副作用），那么应该使用dorun
(dorun (take 5 (random-ints 50)))
;realizing random number
;realizing random number
;realizing random number
;realizing random number
;realizing random number
;nil


