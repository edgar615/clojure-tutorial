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