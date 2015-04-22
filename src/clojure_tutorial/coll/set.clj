(ns clojure-tutorial.coll.set)

;我们把set看做一种退化的map，里面的元素自己映射到自己

(get #{1 2 3} 2)                                            ;;2
(get #{1 2 3} 4)                                            ;;nil
(get #{1 2 3} 4 "not-found")                                ;;"not-found"

;set接口需要disj来从set里面移除一个元素
(disj #{1 2 3} 3 1)                                         ;;#{2}