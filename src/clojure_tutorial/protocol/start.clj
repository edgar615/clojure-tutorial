(ns
  ^{:author edgar}
  clojure_tutorial.protocol.start)

;Clojure里面接口的对应物我们称为协议（Protocol）
;一个协议有一个或多个方法组成，而且每个方法可以有多个方法体。所有的方法至少有一个参数，这个参数对于到java里面的this

;协议里面每个方法的第一个参数是特殊的，因为到底使用这个协议的哪个实现就是根据第一个参数来决定的。
;协议提供的是基于单个参数类型的函数分派，这是一种很受限的多态分派方式。

;协议的定义
(defprotocol ProtocolName
  "documentation"
  (a-method [this arg1 arg2] "method docstring")
  (another-method [x] [x arg] "docstring"))
;因为第一个特殊参数是显式指定的，所以可以使用任何你喜欢的名字this、self或者_等

;跟Clojure中其他内容的命名方式都不一样的是，协议的命名用的是驼峰风格，因为它们最终会被编译成JVM的接口和类

;Matrix协议，定义了对二维顺序数据结构的访问和更新操作：
(defprotocol Matrix
  "Protocol for working with 2d datastructures."
  (lookup [matrix i j])
  (update [matrix i j value])
  (rows [matrix])
  (cols [matrix])
  (dims [matrix]))

;扩展已有类型
;将Matrix协议扩展到Clojure的vector上去
(extend-protocol Matrix
  clojure.lang.IPersistentVector
  (lookup [vov i j]
    (get-in vov [i j]))
  (update [vov i j value]
    (assoc-in vov [i j] value))
  (rows [vov]
    (seq vov))
  (cols [vov]
    (apply map vector vov))
  (dims [vov]
    [(count vov) (count (first vov))]))
;;不需要实现协议定义的所有方法：如果调用到那些没有实现的方法，Clojure会简单地抛出一个异常

;extend-protocol并不是对一个协议进行扩展的唯一方法，扩展的方法还有：
;内联实现
;extend
;extend-type 把多个协议扩展到一个类型
;extend-protocol 把一个协议扩展到多个类型

;还可以把协议扩展到nil
(extend-protocol Matrix
  nil
  (lookup [x i j])
  (update [x i j value])
  (rows [x] [])
  (cols [x] [])
  (dims [x] [0 0]))

(lookup nil 5 5)
;;nil
(dims nil)
;;[0 0]

(defn vov
  "Create a vector of h w-item vectors."
  [h w]
  (vec (repeat h (vec (repeat w nil)))))
(vov 3 4)
;;[[nil nil nil nil] [nil nil nil nil] [nil nil nil nil]]

(def matrix (vov 3 4))
matrix
;;[nil nil nil nil] [nil nil nil nil] [nil nil nil nil]]
(update matrix 1 2 :x)
;;[[nil nil nil nil] [nil nil :x nil] [nil nil nil nil]]
(lookup *1 1 2)
;;:x
(rows (update matrix 1 2 :x))
;;([nil nil nil nil] [nil nil :x nil] [nil nil nil nil])
(cols (update matrix 1 2 :x))

;把一个协议扩展到Java的数组
(extend-protocol Matrix
  (Class/forName "[[D")
  (lookup [matrix i j]
    (aget matrix i j))
  (update [matrix i j value]
    (let [clone (aclone matrix)]
      (aset clone i
        (doto (aclone (aget clone i))
          (aset j value)))
      clone))
  (rows [matrix]
    (map vec matrix))
  (cols [matrix]
    (apply map vector matrix))
  (dims [matrix]
    (let [rs (count matrix)]
      (if (zero? rs)
        [0 0]
        [rs (count (aget matrix 0))]))))

(def matrix (make-array Double/TYPE 2 3))
(rows matrix)
;;([0.0 0.0 0.0] [0.0 0.0 0.0])
(rows (update matrix 1 1 3.4))
;;([0.0 0.0 0.0] [0.0 3.4 0.0])
(lookup (update matrix 1 1 3.4) 1 1)
;;3.4
(cols (update matrix 1 1 3.4))
;;([0.0 0.0] [0.0 3.4] [0.0 0.0])
(dims matrix)
;;[2 3]