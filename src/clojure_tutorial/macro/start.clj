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
(defn square
  [x]
  (* x x))
(map square (range 10))
;;(0 1 4 9 16 25 36 49 64 81)

(defmacro square-macro
  [x]
  `(* ~x ~x))
(map square-macro (range 10))

;如果想在这种上下文里面使用宏，需要把宏包在一个fn或者匿名函数字面量里面。
;这使得宏的应用又返回到编译期。
(map #(macro-hello %) ["Brain" "Not Brian"])
;;("Hello, Brain!" "Hello, Not Brian!")

(map (fn [x] (square-macro x)) (range 10))
;;(0 1 4 9 16 25 36 49 64 81)

;应该只在需要自己的语言组件时才使用宏：只有在函数满足不了需要的时候才去使用宏。
;宏的使用场景主要有：
;需要特殊的求值语义
;需要自定义的语法
;需要在编译期提前计算一些中间值

;宏卫生
;宏产生的代码通常会被嵌在外部代码中使用，而且我们通常也会把一段用户代码作为参数传入宏。
;在任何一种情况下，有一些符号已经被用户代码绑定到一个值了。那么就会有这样一种可能：
;在宏里面使用的某个符号名跟外部代码或者传入的用户自定义代码里面的某个符号名字可能会发生冲突，这样的问题是很难定位的。

(defmacro unhygienic
  [& body]
  `(let [x :oops]
     ~@body))
(unhygienic (println "x:" x))
;;CompilerException java.lang.RuntimeException: Can't let qualified name: user/x,

(macroexpand-1 '(unhygienic (println "x:" x)))
;;(clojure.core/let [user/x :oops] (println "x:" x))

;使用引述、反引述修复上述错误
(defmacro still-unhygienic
  [& body]
  `(let [~'x :oops]
     ~@body))
(still-unhygienic (println "x:" x))
;;x: :oops

;~'x是使用反引述~来强制使用没有命名空间限定的符号x作为let里面绑定的名字

(let [x :this-is-important]
  (still-unhygienic
    (println "x:" x)))
;;x: :oops
;上述代码已经把x绑定到一个本地的值了，但是由宏产生的let会悄悄地把x绑定到另外一个值。

;Gensym
;当要在宏里面建立一个本地绑定的时候，我们希望可以动态产生一个永远不会跟外部代码或用户传入宏的代码冲突的名字。
;gensym函数返回一个保证唯一的符号。每次调用它都能返回一个新的符号
(gensym)
;;G__40
(gensym)
;;G__43

;gensym也可以接受一个参数，这个参数作为产生的符号的前缀
(gensym "sym")
;;sym46
(gensym "sym")
;;sym49

(defmacro hygienic
  [& body]
  (let [sym (gensym)]
    `(let [~sym :macro-value]
       ~@body)))

(let [x :important-value]
  (hygienic (println "x:" x)))
;;x: :important-value

;在语法引述里面任何以#结尾的符号都会被自动扩展，并且对于前缀相同的符号，它们都会被扩展成同一个符号的名字。
;这个被称为“自动gensym”
(defmacro hygienic
  [& body]
  `(let [x# :macro-value]
     ~@body))
(let [x :important-value]
  (hygienic (println "x:" x)))
;;x: :important-value

;在同一个语法引述的形式里面，对于同一个前缀的所有自动gensym，它们会被转换成同一个符号
`(x# x#)
;;(x__83__auto__ x__83__auto__)
;在一个语法引述形式里面可以对一个gensym进行多次引用，而且读起来写起来都非常自然
(defmacro auto-gensyms
  [& numbers]
  `(let [x# (rand-int 10)]
     (+ x# ~@numbers)))
(auto-gensyms 1 2 3 4 5)
;;21
(auto-gensyms 1 2 3 4 5)
;;17
(macroexpand-1 '(auto-gensyms 1 2 3 4 5))
;;(clojure.core/let [x__86__auto__ (clojure.core/rand-int 10)] (clojure.core/+ x__86__auto__ 1 2 3 4 5))

;对于自动gensym，只能保证在同一个语法引述的形式里面所产生的符号的名字是一样的
[`x# `x#]
;;[x__104__auto__ x__105__auto__]

;让宏的用户来选择名字
;因为宏并不对传给它的参数进行求值，因此我们可以很简单地传一个符号给宏，然后在宏产生的代码里面使用这个符号
(defmacro with
  [name & body]
  `(let [~name 5]
     ~@body))
(with bar (+ 10 bar))
;;15
(with foo (+ 40 foo))
;;45

;重复求值
;使用宏时一个普遍而又隐蔽的问题是重复求值。
;重复求值发生在当传给宏的参数在宏的扩展形式里面出现多次的情况下
(defmacro spy [x]
  `(do
     (println "spied" '~x ~x)
     ~x))
(spy 2)
;;spied 2 2
;;2
(spy (rand-int 10))
;;spied (rand-int 10) 1
;;3

(macroexpand-1 '(spy (rand-int 10)))
;;(do (clojure.core/println "spied" (quote (rand-int 10)) (rand-int 10)) (rand-int 10))
;要避免这种问题，可以引入一个本地绑定
(defmacro spy [x]
  `(let [x# ~x]
     (println "spied" '~x x#)
     x#))
(macroexpand-1 '(spy (rand-int 10)))
;;clojure.core/let [x__131__auto__ (rand-int 10)] (clojure.core/println "spied" (quote (rand-int 10)) x__131__auto__) x__131__auto__)
(spy (rand-int 10))
;;spied (rand-int 10) 4
;;4

;重复求值的问题，意味着你把有些应该在函数里面实现的逻辑写到宏里面去了。
(defn spy-helper [expr value]
  (println expr value)
  value)
(defmacro spy [x]
  `(spy-helper '~x ~x))
(spy (rand-int 10))
;;(rand-int 10) 5
;;5


;隐藏参数 &env和&form
;defmacro宏引入了两个隐藏的本地绑定：&env和&form

;&env是一个map，map的key是当前上下文所有本地绑定的名字（而对应的值是未定义的）
;&form里面的元素是当前被宏扩展的整个形式，也就是说，它是一个包含了宏的名字（用户代码里面引用宏的名字——可能被重命名了）
;以及传给宏的所有参数的一个列表。这个形式也就是reader在读入宏的时候所读入的形式。
(defmacro info-about-caller
  []
  (pprint {:form &form :env &env})
  `(println "macro was called!"))

(info-about-caller)
;;{:form (info-about-caller), :env nil}
;;macro was called!

(let [foo "bar"] (info-about-caller))
;;{:form (info-about-caller),
;;:env {foo #<LocalBinding clojure.lang.Compiler$LocalBinding@3d8d5c99>}}
;;macro was called!

(let [foo "bar" baz "quux"] (info-about-caller))
;;{:form (info-about-caller),
;; :env
;;       {baz #<LocalBinding clojure.lang.Compiler$LocalBinding@54892e69>,
;;        foo #<LocalBinding clojure.lang.Compiler$LocalBinding@3b1160b9>}}
;;macro was called!

;The &env value seems pretty magical: it’s a map of local variables,
; where the keys are symbols and the values are instances of some class in the Clojure compiler internals

(defmacro inspect-caller-locals []
  (->> (keys &env)
    (map (fn [k] [`'~k k]))
    (into {})))

(inspect-caller-locals)
;;{}

(let [foo "bar" baz "quux"] (inspect-caller-locals))
;;{baz "quux", foo "bar"}

(defmacro spy-env []
  (let [ks (keys &env)]
    `(prn (zipmap '~ks [~@ks]))))

(let [x 1 y 2]
  (spy-env)
  (+ x y))
;;{x 1, y 2}
;;3


;在宏里面打印有用的错误信息
(defmacro ontology
  [& triples]
  (every? #(or (== 3 (count %))
             (throw (IllegalArgumentException. "must have 3 elements")))
    triples)
  ;;todo
  )
(ontology ["Boston" :capital-of])
;;IllegalArgumentException must have 3 elements  user/ontology/fn--158 (NO_SOURCE_FILE:113)
(pst)
;;IllegalArgumentException must have 3 elements
;;user/ontology/fn--158 (NO_SOURCE_FILE:113)

;这里显示的行号113从用户的角度来说是不对的,它是抛异常位置相对宏的源码定义开始的行号，而不是调用这个宏的代码的行号
(defmacro ontology
  [& triples]
  (every? #(or (== 3 (count %))
             (throw (IllegalArgumentException.
                      (format "'%s' provided to '%s' on line %s has < 3 elements"
                        %
                        (first &form)
                        (-> &form meta :line)))))
    triples)
  ;;todo
  )
(ontology ["Boston" :capital-of])
;;IllegalArgumentException '["Boston" :capital-of]' provided to 'ontology' on line 131 has < 3 elements  user/ontology/fn--169 (NO_SOURCE_FILE:127)

;用户在一个命名空间里面引入这个宏的时候可能利用refer之类的函数对宏进行了重命名，
; 它可以避免从别的空间里面引入函数、宏的时候与当前空间里面的函数、宏的名字发生冲突
(ns com.edgar.macros)
(refer 'user :rename '{ontology triples})
(triples ["Boston" :capital-of])
;;IllegalArgumentException '["Boston" :capital-of]' provided to 'triples' on line 134 has < 3 elements  user/ontology/fn--169 (NO_SOURCE_FILE:127)


;深入->和->>
(defn ensure-seq [x]
  (if (seq? x) x (list x)))
(ensure-seq 'x)
;;(x)
(ensure-seq '(x))
;;(x)

(defn insert-second
  "Insert x as the seconde item in seq y."
  [x ys]
  (let [ys (ensure-seq ys)]
    (concat (list (first ys) x)
      (rest ys))))

;可以利用引述和反引述来更简洁地编写这段代码。引述不只可以在宏里面使用
(defn insert-second
  "Insert x as the seconde item in seq y."
  [x ys]
  (let [ys (ensure-seq ys)]
    `(~(first ys) ~x ~@(rest ys))))

;也可以利用list*来写得更简洁
(defn insert-second
  "Insert x as the seconde item in seq y."
  [x ys]
  (let [ys (ensure-seq ys)]
    (list* (first ys) x (rest ys))))

(defmacro thread
  "Thread x through successive forms."
  ([x] x)
  ([x form] (insert-second x form))
  ([x form & more] `(thread (thread ~x ~form) ~@more)))

(thread [1 2 3] (conj 4) reverse println)
;;(4 3 2 1)
(-> [1 2 3] (conj 4) reverse println)
;;(4 3 2 1)

;->>
;->>和->很类似，只是它是把前面一个form插入到后面一个form的最后一个元素的位置上，而不是第二个元素的位置上
;这个宏经常被用来对一个序列或者其他数据结构进行转换
(->> (range 10) (map inc) (reduce +))
;;55

