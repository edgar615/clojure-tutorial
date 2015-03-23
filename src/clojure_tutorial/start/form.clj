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