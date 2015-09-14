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
(ns use2
  (:import (taoensso.nippy MyType)))
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


;对一个字段进行更新也只要简单调用assoc就好了，所以其他关系型集合相关的函数keys、get、seq、conj、into等都可以用在记录类型上。
; 并且更Clojure map一样，记录类型也实现了Java的util.map接口，所有可以把记录类型传给任何需要JavaMap的函数/方法类使用

;虽然在定义的时候指定了记录类型有哪些字段，但是你还是可以在运行时给它添加新的字段：
(assoc (Point. 3 4) :z 5)
;;#user.Point{:x 3, :y 4, :z 5}

(let [p (assoc (Point. 3 4) :z 5)]
  (dissoc p :x))
;;{:y 4, :z 5}
;;从记录类型实例去掉一个预定义的字段的话，返回的就不是记录类型了，而是被降级成一个普通的map

(let [p (assoc (Point. 3 4) :z 5)]
  (dissoc p :z))
;;#user.Point{:x 3, :y 4}
;;如果去除的是运行时额外添加的字段，那么返回的仍然是记录类型，而不会发生降级

;这些运行时额外添加的字段是被保存在一个单独的Clojure hashmap里面的，因此它们的语义也是普通map的定义——它们并美柚被添加到底层那个Java类上去

(:z (assoc (Point. 3 4) :z 5))
;;5
(.z (assoc (Point. 3 4) :z 5))
;;IllegalArgumentException No matching field found: z for class user.Point

;元数据支持
;可以通过meta来获取记录的元数据信息，通过with-meta或vary-meta来设置记录的元数据信息——而不会影响记录的值语义
(-> (Point. 3 4)
    (with-meta {:foo :bar})
    meta)
;;{:foo :bar}

;可读的表示法
;可以从记录的一个文本表示读入一个记录实例——跟Clojure的其他字面量一样
(pr-str (assoc (Point. 3 4) :z [:a :b]))
;;"#user.Point{:x 3, :y 4, :z [:a :b]}"
(= (read-string *1)
   (assoc (Point. 3 4) :z [:a :b]))
;;true

;附加构造函数
;除了接受预定义的标准构造函数，记录类型还提供了一个附加的构造函数，这个构造函数接受两个额外的参数：一个包含额外字段的map以及一个包含元数据的map
(Point. 3 4 {:foo :bar} {:z 5})
;;#user.Point{:x 3, :y 4, :z 5}

(meta *1)
;;{:foo :bar}

;这个在语义上跟下面的代码是等价的，但是更高效
(-> (Point. 3 4)
    (with-meta {:foo :bar})
    (assoc :z 5))

;构造函数与工厂函数
;构造函数通常不应该作为你的公开api的一部分，相反，应该对于你的类型提供一些工厂函数，因为
;1.工厂函数可能更适合调用者使用，因为由底层deftype或defrecord生成的构造函数通常太底层，包含了一些调用者可能根本不关心的细节在里面
;2.可以把工厂函数作为普通函数一样传给其他高阶函数，以对生成的记录进行处理。
;3.可以最大化你的API的稳定性——即使在底层模型发生变化的时候

;deftype和defrecord都会隐式创建一个形如->MyType的工厂函数，它接受的参数跟定义类型时候的字段列表一样：
(->Point 3 4)
;;#user.Point{:x 3, :y 4}

;记录类型还隐式生成另外一个工厂函数map-MyType，它接受一个map作为参数，这个map包含了要填充给新MyType实例的信息
(map->Point {:x 3 :y 4 :z 5})
;;#user.Point{:x 3, :y 4, :z 5}

;这些对于创建普通类型以及记录类型抖森很有用的，特别是在跟高阶函数一起使用的时候：
(apply ->Point [5 6])
;;#user.Point{:x 5, :y 6}


(map (partial apply ->Point) [[5 6] [7 8] [9 10]])
;;(#user.Point{:x 5, :y 6} #user.Point{:x 7, :y 8} #user.Point{:x 9, :y 10})

(map map->Point [{:x 1 :y 2} {:x 5 :y 6 :z 44}])
;;(#user.Point{:x 1, :y 2} #user.Point{:x 5, :y 6, :z 44})

;对于记录类型，这个map-MyType函数还可以通过静态方法create来访问，这对于Java调用者来说很方便
(Point/create {:x 3 :y 4 :z 5})
;;#user.Point{:x 3, :y 4, :z 5}

;虽然提供的这些工厂函数很有用，但是还是会需要编写自己的工厂函数，比如你可以在你的工厂函数里面添加一些校验逻辑等：
(defn log-point
  [x]
  {:pre [(pos? x)]}
  (Point. x (Math/log x)))

(log-point -42)
;;AssertionError Assert failed: (pos? x)

(log-point Math/E)
;;#user.Point{:x 2.718281828459045, :y 1.0}


;什么时候使用map，什么时候使用记录类型
;虽然很多场景都适合使用记录类型，但是通常鼓励先使用map，然后在实在需要的时候在换成记录类型。

;记录类型不是函数
;普通map和记录类型永远不可能相等

(defrecord Point [x y])
(= (Point. 3 4) (Point. 3 4))
;;true
(= {:x 3 :y 4} (Point. 3 4))
;;false
(= (Point. 3 4) {:x 3 :y 4})
;;false



;类型
;deftype是Clojure里面最底层的定义形式，defrecord其实是包装了deftype功能的一个宏。
;很多由记录类型所提供的方便的特性在deftype所定义的类型里面是没有的
;它本身就是被设计定义那种最底层的框架类型的，比如定义一种新的数据结构或者引用类型。
;而相对应来说，普通map以及记录类型则是应该用来表示你的应用级别的数据的

;而这种低级别的类型确实提供了一种在编写底层应用或者库时“有时候”所不能提供的特性：可修改的字段。

;对于由deftype定义的，普通(不可修改)字段的访问只能通过java互操作的语法

(deftype Point [x y])
(.x (Point. 3 4))
;;3
(:x (Point. 3 4))
;;nil

;deftype美柚实现关系型数据结构的接口，所有那种使用关键字来访问字段内容的函数在这种类型里面是没办法用的。
;所以我们必须依赖这个事实：这个定义的类型最终会被编译成java类，而所有的不可修改字段都被定义成这个java类型里面的public final字段

;可修改字段则有两种类型：volatile或者非synchronized的。要把一个字段定义成可修改的，可以给它加上元数据^:volatile-mutable活摘^:unsynchronized-mutable
(deftype MyType [^:volatile-mutable fld])

;我们定义的不可修改字段始终是public的，但是可修改字段则始终是private的，并且只能在定义类型的那个形式的那些内联方法里面使用。

;定义一个实现了IDered接口的deref方法的类型
(deftype Cat [^:unsynchronized-mutable state]
  clojure.lang.IDeref
  (deref [sc]
    (locking sc
      (or state
          (set! state (if (zero? (rand-int 2))
                :dead
                :alive))))))

(defn cat
  "create a new cat...."
  []
  (Cat. nil))

(def felix (cat))
@felix
;;:dead
(cat)
;;#<Cat@137c1eb6: :dead>
(cat)
;;#<Cat@5954b330: :alive>
