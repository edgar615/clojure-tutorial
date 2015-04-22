(ns clojure-tutorial.coll.indexed)

;Index接口提供了操作下标的各种函数。这个接口里面只有一个函数：nth，它和get类似，不同的是对于越界的下标的处理：nth会抛出异常，而get则是返回nil

(nth [:a :b :c] 2)                                          ;;:c
(get [:a :b :c] 2)                                          ;;:c

(nth [:a :b :c] 3)                                          ;;IndexOutOfBoundsException
(get [:a :b :c] 3)                                          ;;nth

(nth [:a :b :c] -1 "not-found")                             ;;"not-found"
(get [:a :b :c] -1 "not-found")                             ;;"not-found"

;nth和get更大的区别是，get对错误更容忍。除对越界的容忍外，get还可以容忍你传给它一个部支持的参数类型
(get 42 0)                                                  ;;nil
(nth 42 0)                                                  ;;UnsupportedOperationException nth not supported on this type: Long
