(ns clojure-tutorial.protocol.protocol
  (:import (clojure_tutorial.protocol.start Matrix)
           (clojure_tutorial.protocol.protocol ClashWhenInlined Measurable)))

;要让一个给定的类型实现一个协议有两种方法
;1.在用deftype或者defrecord定义类型的时候直接把这些方法给实现了，这种方法叫做内联实现
;2.使用extend*系列函数来把一个类型的实现注册到协议上去

;内联实现
(defrecord Point [x y]
  Matrix
  (lookup [pt i j]
    (when (zero? j)
      (case i
        0 x
        1 y)))
  (update [pt i j value]
    (if (zero? j)
      (condp = i
        0 (Point. value y)
        1 (Point. x value))
      pt))
  (rows [pt] [[x y]])
  (cols [pt] [[x y]])
  (dims [pt] [2 1]))

;扩展
(defrecord Point [x y])

(extend-protocol Matrix
  Point
  (lookup [pt i j]
    (case i
      0 x
      1 y))
  (update [pt i j value]
    (if (zero? j)
      (condp = i
        0 (Point. value y)
        1 (Point. x value))
      pt))
  (rows [pt] [[(:x pt) (:y pt)]])
  (cols [pt] [[(:x pt) (:y pt)]])
  (dims [pt] [2 1]))

;这两种方式的细微区别是如何访问字段的值：当你在类型之外访问类型的字段时你需要以关键字（或者Java互操作方法）来访问，
;而当你内联实现的时候可以直接引用字段名字来获取字段的值，因为内联的时候这些字段名在词法范围内

;内联实现
;一般来说，内联实现的性能会好一些：内联实现的时候能直接访问类型的字段：内联实现的时候调用协议的方法跟java里面调用一个接口的方法一样快
;因为每个协议最终是被编译成一个java的接口，以内联方式实现的一个协议的方法会产生一个实现了协议方法的java类,而那个java类的实现旧对应的协议的方法的实现

;如果两个协议有同样签名的方法，那么这两个方法就会冲突
;如果你尝试提供那些defrecord会自动提供的方法实现，包括java.util.Map，java.io.Serializable以及clojure.lang.IPersistentMap里面的方法，那么会抛出一个错误
(defprotocol ClashWhenInlined
  (size [x]))

(defrecord R []
  ClashWhenInlined
  (size [x]))
;;CompilerException java.lang.ClassFormatError: Duplicate method name&signature in class file user/R

(defrecord R [])
(extend-type R
  ClashWhenInlined
  (size [x]))
;nil

;用extend-type来把一个类型的实现注册到协议就不会跟java.util.Map的size方法冲突，因为这个注册的行为不会影响到R类型本身的创建——为了实现这个协议，这个类本身并没有发生什么变化
;因为使用内联实现的时候，这些实现代码是直接写入deftype或defrecord所定义的类文件里面的，所有除非你重新定义整个类型，否则无法在运行时改变这些实现。
;所以应该只在进行性能优化的时候才去考虑这种实现方法

;作为这个通用规则的一个特例，要实现Java的接口的话，只能用这种方法
;作为一个特例，可以把java.lang.Object的方法也作为接口来实现

;java接口的内联实现
(deftype MyType [a b c]
  java.lang.Runnable
  (run [this]
    ;...
    )
  Object
  (equals [this that]
    ;...
    )
  (hashcode [this]
    ;...
    )
  ;(Protocle1 [this ...] ...)
  )

;使用reify来定义匿名类型
;跟deftype和defrecord不同的是，reify不是一个顶级形式，它不会定义出一个有名类型，它直接求值除一个无名类型的实例。
;这是一种创建实现任何协议（或者实现任何接口或者Object的方法）的类实例的方法。它其实对应到java里面的匿名类
(reify
  Protocol-or-Interface-or-Object
  (method1 [this x]
    (implementation))
  Another-Protocol-or-Interface
  (method2 [this x y]
    (implementation))
  (method3 [this x]
    (implementation)))

;由reify所创建的类实例创建了一个闭包，使得方法的实现体可以直接访问当前词法范围内的任何本地绑定。这个对于创建适配器活摘创建一次性的对象是非常方便的
;把一个函数包装成一个ActionListener
(defn listener
  "create an AWT/Swing ActionListener ..."
  [f]
  (reify
    java.awt.event.ActionListener
    (actionPerformed [this e]
      (f e))))

;使用一个reify的FileFilter实现来获取目录文件
(.listFiles (java.io.File. ".")
            (reify
              java.io.FileFilter
              (accept [this f]
                (.isDirectory f))))

;重用实现
;在Clojure的协议与类型的体系里面，没有”层级“的概念。
;类型只能实现协议或者实现接口——没有像其他语言里面一样让一个类型去继承另一个具体类型，从而继承它的实现
;Clojure对这个问题的解决方法是利用extend-type和extend-protocol宏的基础: extend来重用方法实现

;extend接受的第一个参数是要进行扩展的类型，然后要实现的协议的名字以及具体的方法实现的map，这个map以方法名作为key，map的value就是这个方法的实现
(defrecord Point [x y])
(extend Point
  Matrix
  {:lookup (fn [pt i j]
             (when (zero? j)
               (case i
                 0 (:x pt)
                 1 (:y pt))))
   :update (fn [pt i j value]
             (if (zero? j)
               (condp = i
                 0 (Point. value (:y pt))
                 1 (Point. (:x :pt) value))))
   :rows (fn [pt]
           [[(:x pt)] [(:y pt)]])
   :cols (fn [pt]
           [[(:x pt) (:y pt)]])
   :dims (fn [pt] [2 1])})

;为一些方法提供默认实现
(def abastract-matrix-impl
  {:cols (fn [pt]
           (let [[hw] (dims pt)]
             (map
               (fn [x] (map #(lookup pt x y) (range 0 w)))
               (range 0 h))))
   :rows (fn [pt]
           (apply map vector [cols pt]))})

;现在要把Matrix扩展到Point类型，只需把默认的实现map assoc到具体类型的实现map上去旧可以了
(extend Point
  Matrix
  (assoc abastract-matrix-impl
    :lookup (fn [pt i j]
              (when (zero? j)
                (case i
                  0 (:x pt)
                  1 (:y pt))))
    :update (fn [pt i j value]
              (if (zero? j)
                (condp = i
                  0 (Point. value (:y pt))
                  1 (Point. (:x :pt) value))))
    :dims (fn [pt] [2 1])))

;mixins特性：一个方法的实现是把其他几个具体的实现通过某种有意义的方式组合起来

;定义一个协议
(defprotocol Measurable
  "A protocol for retrieving the dimensions of widgets."
  (width [measurable] "Returns the width in px.")
  (height [measurable] "Returns the height in px."))
;定义一个新类型
(defrecord Button [text])
(extend-type Button
  Measurable
  (width [btn]
    (* 8 (-> btn :text count)))
  (height [btn] 8))

;定义一个方法
(def bordered
  {:width #(* 2 (:border-width %))
   :height #(* 2 (:border-height %))})

;定义新BorderedButton类
(defrecord BorderButton [text border-width border-height])

(extend BorderButton
  Measurable
  (merge-with (partial combine +)
              (get-in Measurable [:impl Button])
              bordered))

(let [btn (Button. "Hello World")]
  [(width btn) (height btn)])

(let [bbtn (BorderButton. "Hello World" 6 4)]
  [(width bbtn) (height bbtn)])


;协议自省
;extenders 返回实现了某个协议的所有类
(extenders Measurable)
;;(user.Button)

;extend? 如果一个类型扩展了一个协议，那么返回true
(extends? Measurable Button)
;;true

;satisfies? 它是clojure世界的instance?
(satisfies? Measurable (Button. "hello"))
;;true

;协议函数分派的边界场景