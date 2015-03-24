(ns clojure-tutorial.start.form)

;阻止求值：quote
;quote阻止Clojure表达式的求值
(quote x)
(symbol? (quote x))
;quote有对应的reader语法：单引号'。reader在求值到单引号的时候会把它解析成一个quote
'x
;任何Clojure形式都可以被quote，包括数据结构。如果使用数据结构，那么返回的是数据结构本身(数据结构内的表达式将不作求值操作)
'(+ x x)                                                    ;(+ x x)
(list? '(+ x x))                                            ;true
(list '+ 'x 'x)                                             ;(+ x x)
;quote的一个有趣应用就是探查reader对于任意一个形式的求值结果
''x                                                         ;(quote x)
'@x                                                         ;(clojure.core/deref x)
'#(+ % %)                                                   ;(fn* [p1__786#] (+ p1__786# p1__786#))
'`(a b ~c)                                                  ;(clojure.core/seq (clojure.core/concat (clojure.core/list (quote user/a)) (clojure.core/list (quote user/b)) (clojure.core/list c)))

;代码块do
;do会依次求值你传进来的所有表达式，并且把最后一个表达式的结果作为返回值
(do
  (println "hi")
  (apply * [4 5 6]))
;hi
;120
;这些表达式的值除了最后一个都被丢弃了，但是它们的副作用还是发生了。
;很多其他形式，包括fn、let、loop、try、defn以及这些形式的变种都隐式地使用了do形式
(let [a (inc (rand-int 10))
      b (inc (rand-int 10))]
  (println (format "You rooled a %s and a %s" a b)))


;定义Var：def
;def的作用是在当前命名空间里定义（或重定义）一个var（你可以同时给它赋一个值，也可以不赋值）
(def p "foo")
p

;本地绑定：let
(defn hypot
  [x y]
  (let [x2 (* x x)
        y2 (* y y)]
    (Math/sqrt (+ x2 y2))))
;在任何需要本地绑定的地方都间接地使用了let。比如fn使用let来绑定函数参数作为函数体内的本地绑定
;我们会在let的绑定数组里面对一个表达式求值，但是我们对于表达式的值并不关系，也不会去使用这个值。 在这种情况下，通常使用_来指定这个绑定的名字

;解构
;定义一个vector
(def v [42 "foo" 99.2 [5 12]])
(first v)                                                   ;42
(second v)                                                  ;"foo"
(last v)                                                    ;[5 12]
(nth v 2)                                                   ;99.2
(v 2)                                                       ;99.2
(.get v 2)                                                  ;99.2

;顺序解构
;顺序解构可以对任何顺序集合近线解构
;Clojure原生的list、vector以及seq
;任何实现了java.util.List接口的集合（比如ArrayList和LinkedList）
;java数组
;字符串，对它解构的结果是一个个字符

(def v [42 "foo" 99.2 [5 12]])
(let [[x y z] v]
  (+ x z))                                                  ;141.2

(let [x (nth v 0)
      y (nth v 1)
      z (nth v 2)]
  (+ x z))                                                  ;142.2
;解构的形式还支持嵌套的解构形式
(let [[x _ _ [y z]] v]
  (+ x y z))                                                ;59
;保持剩下的元素
;可以使用&符号来保持解构剩下的那些元素
(let [[x & rest] v]
  rest)                                                     ;("foo" 99.2 [5 12])
;rest是一个序列，而不是vector，虽然被解构的参数v是一个vector

;保持被解构的值
;可以在解构形式中指定:as选项来把被解构的原始集合绑定到一个本地绑定
(let [[x _ z :as original-vector] v]
  (conj original-vector (+ x z)))                           ;[42 "foo" 99.2 [5 12] 141.2]
;这里的original-vector被绑定到未做修改的集合v

