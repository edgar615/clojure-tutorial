(ns clojure-tutorial.coll.collection)

;Clojure中的所有数据结构都实现了Collection抽象。对于Collection，可以使用如下这些核心的集合函数
;用conj来添加一个元素到集合
;用seq来获取集合的顺序视图
;用count来活取集合的元素个数
;用empty来获取一个跟所提供集合类型一样的空集合
;用=来判断两个或多个集合是否相等

;conj的使用参考conj函数