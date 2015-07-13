(ns
  ^{:author edgar}
  clojure_tutorial.macro.start
  (require '(clojure [string :as str]
              [walk :as walk])))

;(defmacro foreach [[sym coll] & body]
;  `(loop [coll# ~coll]
;     (when-let [[~sym & xs#] (seq coll#)]
;       ~@body
;       (recure xs#))))
;
;(foreach [x [1 2 3]]
;  (println x))

(defmacro print-keyword [x]
  `(println (keyword ~x)))

(print-keyword "foo")
;;foo

(defmacro reverse-it
  [form]
  (walk/postwalk #(if (symbol? %)
                    (symbol (str/reverse (name %)))
                    %)
    form))

(reverse-it
  (qesod [gra (egnar 5)]
    (nltnirp (cni gra))))
;;1
;;2
;;3
;;4
;;5

;展开宏
(macroexpand-1 '(reverse-it
                  (qesod [gra (egnar 5)]
                    (nltnirp (cni gra)))))
;;(doseq [arg (range 5)] (println (inc arg)))

;macroexpand-1只会扩展宏一次，如果一个宏被扩展后还包含了对宏的调用的话，那么宏需要扩展多次
;如果宏扩展之后产生的是对另外一个宏的调用，而你想继续扩展这个宏到最顶级的形式不再是一个宏的话，使用macroexpand


(pprint (macroexpand '(reverse-it
                        (qesod [gra (egnar 5)]
                          (nltnirp (cni gra))))))

;完全宏扩展
;macroexpand-1和macroexpand都不会对嵌套的宏进行扩展。
(macroexpand '(cond a b c d))

;获得一个宏的彻底扩展
(walk/macroexpand-all '(cond a b c d))
;;(if a b (if c d nil))

;语法
;因为宏要返回Clojure数据结构，我们经常返回列表以表示进一步调用，
; 这个调用可能是对函数、特殊形式或者宏的调用。
(defmacro hello
  [name]
  (list 'println name))

(macroexpand '(hello "Brian"))
;;(println "Brian")



;引述和语法引述
;语法引述把无命名空间限定的符号求值成当前命名空间的符号
(def foo 123)

[foo (quote foo) 'foo `foo]
;;[123 foo foo user/foo]

;语法引述允许反引述：一个列表里面的某些元素可以被选择性地反引述，从而使得它们在语法引述的形式内被求值

;反引述与编接反引述
;在编写一个宏的骨架的时候，我们通常想保持列表里面的一些元素不进行求值，而另外一些元素则需要求值。
(list `map `println [foo])
;;(clojure.core/map clojure.core/println [123])

;一个更简短、更有可读性的办法是把整个列表进行语法引述，然后把其中那些需要求值的元素进行反引述
;可以通过~来反引述
`(map println [~foo])
;;(clojure.core/map clojure.core/println [123])

;反引述一个列表或者vector会把整个形式都反引述。
`(map println ~[foo])
;;(clojure.core/map clojure.core/println [123])

`(println ~(keyword (str foo)))
;;(clojure.core/println :123)


;引述
(def a 4)

'(1 2 3 a 5)
;;(1 2 3 a 5)
(list 1 2 3 a 5)
;;(1 2 3 4 5)
'a
;;a

;语法引述
(def a 4)
`(1 2 3 ~a 5)
;;(1 2 3 4 5)
`~a
;;4

'~a
;;(clojure.core/unquote a)
`~'a
;;a

`(1 2 3 '~a 5)
;;(1 2 3 (quote 4) 5)

`(1 2 3 (quote (clojure.core/unquote a)) 5)
;;(1 2 3 (quote 4) 5)

(def other-nums '(4 5 6 7))

`(1 2 3 ~other-nums 9 10)
;;(1 2 3 (4 5 6 7) 9 10)
`(1 2 3 ~@other-nums 9 10)
;;(1 2 3 4 5 6 7 9 10)


;有一个列表的形式，然后想把另外一个列表的内容解开加入到第一个列表里面去。
(let [defs '((def x 123)
              (def y 456))]
  (concat (list 'do) defs))
;;(do (def x 123) (def y 456))
;编接反引述操作符'@是一个更好的办法，它自动帮你做列表的链接
(let [defs '((def x 123)
              (def y 456))]
  `(do ~@defs))
;;(do (def x 123) (def y 456))

;一个接受多个形式作为“代码体”的宏大概是这样：
(defmacro foo
  [& body]
  `(do-something ~@body))

(macroexpand-1 '(foo (doseq [x (range 5)]
                       (println x))
                  :done))
;;(user/do-something (doseq [x (range 5)] (println x)) :done)


;宏的例子
(defmacro squares
  [xs]
  (list 'map '#(* % %) xs))

(squares (range 10))
;;(0 1 4 9 16 25 36 49 64 81)

(defmacro squares2
  [xs]
  `(map #(* % %) ~xs))

(squares2 (range 10))
;;(0 1 4 9 16 25 36 49 64 81)

(defmacro squares3
  [xs]
  `(map (fn [~'x] (* ~'x ~'x)) ~xs))

(squares3 (range 10))
;;(0 1 4 9 16 25 36 49 64 81)


(defmacro make-adder
  [x]
  `(fn [~'y] (+ ~x ~'y)))

(macroexpand-1 '(make-adder 10))
;;(clojure.core/fn [y] (clojure.core/+ 10 y))


;什么时候使用宏
;宏是在编译期被调用的
(defn fn-hello [x]
  (str "Hello, " x "!"))

(defmacro macro-hello [x]
  `(str "Hello, " ~x "!"))
;它们在一些用法上表现是类似的
(fn-hello "Brian")
;;"Hello, Brian!"
(macro-hello "Brian")
;;"Hello, Brian!"

;而在另外一些上下文中表现就不一样了
(map fn-hello ["Brain" "Not Brian"])
;;("Hello, Brain!" "Hello, Not Brian!")
(map macro-hello ["Brain" "Not Brian"])
;;CompilerException java.lang.RuntimeException: Can't take value of a macro:
;宏不能作为值来进行组合或者传递。宏根本就没有运行时值的概念。

;如果想在这种上下文里面使用宏，需要把宏包在一个fn或者匿名函数字面量里面。
;这使得宏的应用又返回到编译期。
(map #(macro-hello %) ["Brain" "Not Brian"])
;;("Hello, Brain!" "Hello, Not Brian!")

;应该只在需要自己的语言组件时才使用宏：只有在函数满足不了需要的时候才去使用宏。
;宏的使用场景主要有：
;需要特殊的求值语义
;需要自定义的语法
;需要在编译期提前计算一些中间值

;宏卫生
