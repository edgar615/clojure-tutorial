(ns clojure-tutorial.start.type
  (:import (org.apache.http.client.utils Rfc3492Idn)))

;字符串
"Hello world!"

"Hello
world!"

;布尔值
true
false

;nil
nil

;字符
\C
;unicode字符
\u00ff
\o41
;特殊字符
\space
\newline
\formfeed
\return
\backspace
\tab

;关键字
(def person {:name "Edgar"
             :city "Wuhan"})
;关键字本身也是函数
(:name person)
(:city person)
;关键字始终以冒号开头，它可以包含任意的非空字符。
;如果关键字里面包含/，表示这个关键字是命名空间限定的，
;而如果一个关键字以两个冒号(::)开头的话，表示是当前命名空间的关键字
;如果一个关键字以两个冒号开头，同时又包含了/的话，比如::alias/kw，那么表示是某个特定命名空间里面的关键字。
(def pizza {:name "Ramunto's"
            :location "Claremont , Nh"
            ::location "43.3734, -72.3365"})
(:user/location pizza)

(name :user/location)
(namespace :user/location)
(namespace :location)

;符号


;数字
;long
42
0xff
2r111
040
;double
3.14
6.0221415e23
;BigInt
42N
;BigDecimal
0.01M
;clojure.lang.Ratio
22/7
;16进制
0xff
0xd055
16rff
;8进制
040
8r111
;任意进制
2r111
8r111
16r111

;正则表达式
#"(p|h)ail"
;clojure中的正则表达式不需要像java那样对反斜杠近线转义
#"(\d+)-(\d+)"

;注释
;单行注释
;
;形式级别的注释 #_ 宏，这个宏会告诉reader忽略下一个clojure形式
(read-string "(+ 1 2 #_(* 2 2) 8)")
;comment宏，comment里面可以包含任意数量的代码，但是跟#_不同的是，comment里面的代码并没有被reader彻底忽略，comment形式的返回值始终是nil
(+ 1 2 (comment (* 2 2)) 8)                                 ;NullPointerException

;空格和逗号
;在reader眼中，逗号就是空格，下面的两段代码是一样的
;空格
(defn silly-add
  [x y]
  (+ x y))
;逗号
(defn silly-add
  [x, y]
  (+, x, y))

(= [1 2 3] [1, 2, 3])                                       ;true

;集合字面量
'(a b :name 12.5)                                           ;list
['a 'b :name 12.5]                                          ;vector
{:name "Edgar" :age 29}                                     ;map
#{1 2 3}                                                    ;set
;由于clojure中石瑛列表来表示函数调用，所以当要表示数据结构的时候要在列表的前面加一个单引号，以防止列表被求值成一个函数调用

;语法糖
;阻止求值 quote '
;匿名函数#()
;引用var #'
;解引用@
;宏的特殊语法：`、~、~@
;与java互操作
;元数据




