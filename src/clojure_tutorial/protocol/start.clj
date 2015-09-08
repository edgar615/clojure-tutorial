(ns
  ^{:author edgar}
  clojure_tutorial.protocol.start
  (:import (javafx.scene.shape ArcType)))

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

;(extend-type ArcType
;  AProtocol
;  (method-from-AProtocol [this x]
;    (
;      ;实现
;      ))
;  AnotherProtocol
;  (method-from-AnotherProtocol [this x]
;    (
;      ;实现
;      )))

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
;(extend-protocol Matrix
;  (Class/forName "[[D")
;  (lookup [matrix i j]
;    (aget matrix i j))
;  (update [matrix i j value]
;    (let [clone (aclone matrix)]
;      (aset clone i
;        (doto (aclone (aget clone i))
;          (aset j value)))
;      clone))
;  (rows [matrix]
;    (map vec matrix))
;  (cols [matrix]
;    (apply map vector matrix))
;  (dims [matrix]
;    (let [rs (count matrix)]
;      (if (zero? rs)
;        [0 0]
;        [rs (count (aget matrix 0))]))))
;
;(def matrix (make-array Double/TYPE 2 3))
;(rows matrix)
;;;([0.0 0.0 0.0] [0.0 0.0 0.0])
;(rows (update matrix 1 1 3.4))
;;;([0.0 0.0 0.0] [0.0 3.4 0.0])
;(lookup (update matrix 1 1 3.4) 1 1)
;;;3.4
;(cols (update matrix 1 1 3.4))
;;;([0.0 0.0] [0.0 3.4] [0.0 0.0])
;(dims matrix)
;;;[2 3]


;定义自己的类型
;一个Clojure类型是一个java类
(defrecord Point [x y])
;或
(deftype Point [x y])
;上述两个方法都会定义出一个新的Point的java类，java类里面有两个以public和final修饰的名为x和y的字段
;跟协议一样，类型的名字采用驼峰风格，因为它们被编译成了Java的类

;创建一个新的Point实例
(Point. 3 4)
;访问字段
(.x (Point. 3 4))

;定义的每个字段抖森java.lang.Object类型。
;如果需要把字段定义成基本类型，那么可以用类型提示来指定。
;可以通过类型提示指定某个字段为非基本类型，但这个不会改变这个字段的具体类型

;定义一个具有long类型的，名字分别为x和y的两个字段，以及一个类型为Object的名字为name的字段(虽然类型提示它是String类型的)
(defrecord NamedPoint [^String name ^long x ^long y])

;记录类型支持在运行时添加字段。这个类里面的所有字段的集合被称为basis，如果你的类型是通过deftype或defrecord定义的，那么可以通过下面的方法获取
(NamedPoint/getBasis)
;;[name x y]
;这个basis里面的每个符号都保持了定义时指定的所有元数据，包括类型信息
(map meta (NamedPoint/getBasis))
;;({:tag String} {:tag long} {:tag long})

;defrecord记录类型是设计来表示应用级别的数据，它和map是Clojure世界的pojo
;而deftype则是设计类表示比较底层的一些类型的，比如如果你要实现一种新的数据结构，那么应该使用deftype

;这两种方式的主要区别在于,defrecord对于所定义的类型提供了与Clojure以及Java进行互操作的一些默认行为。
;而deftype则提供了一些对底层操作进行优化的能力。所以最后你好发现大多时候用的是defrecord，而很少用deftype


;类型并不是命名空间限定的
;当你用defrecord或deftype定义了一个新类型，这个定义号的类型是被定义在跟所在命名空间对于的一个java package里面的，
;而且它会被默认引入所定义的命名空间了，因此你可以以无限定名称去直接引用它。但是如果你切换到另一个命名空间，那么即使以及use或者require了定义它的命名空间，
;还是需要显式地import这个类，因为它是宿主语言类，不是var

(def x "hello")
;;#'user/x

(defrecord Point [x y])
;;user.Point
(Point. 5 5)
;;#user.Point{:x 5, :y 5}
(ns use2)
(refer 'user)
x
;;"hello"
Point
;;CompilerException java.lang.RuntimeException: Unable to resolve symbol

(import 'user.Point)
Point
;;user.Point

;记录
;由defrecord定义的记录类型其实是由deftype定义的类型的一种特例。添加了如下这些额外的特性：
; 值语义
; 实现了关系型数据结构的接口
; 元数据的支持
; 对于Clojure reader的支持，比如我们可以通过Clojure reader直接读入一个记录类型
; 一个额外的、方便的构造函数，使得我们可以在创建实例的时候添加一些元数据以及一些额外的字段

;值语义
;值语义意味着两件事：记录类型是不可变的；如果两个类型的所有对应字段都相等，那么这两个记录本身也是相等的。
(defrecord Point [x y])
(= (Point. 3 4) (Point. 3 4))
;;true
(= 3 3N)
;;true
(= (Point. 3 4) (Point. 3N 4N))
;;true

;记录是一种关系型数据结构
;记录类型实现了关系型数据结构的接口，所有那些对map进行操作的函数你都可以用在记录类型的实例上
(:x (Point. 3 4))
;;3
(:z (Point. 3 4) 0)
;;0
(map :x [(Point. 3 4)
         (Point. 5 6)
         (Point. 7 8)])
;;(3 5 7)
