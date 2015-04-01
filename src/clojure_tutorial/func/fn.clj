(ns clojure-tutorial.func.fn)

;函数本身就是值，它们可以像其他数据一样，作为参数传给其他函数，同时也可以把函数作为返回值


;实现一个函数，它的作用是调用其他函数两次。
(defn call-twice [f x]
  (f x)
  (f x))
(call-twice println "Hello!")

;Clojure的所有函数都是头等公民。它们独立存在，不依附于任何对象或者命名空间，可以作为参数传递给其他函数，也可以被其他函数作为返回值返回。
;它们是数据，就如同数据结构，数字以及字符串一样。

;Clojure函数本身是值，它们可以被高阶函数使用。
;所谓高阶函数，是指接受函数作为参数，或者把函数作为返回值的函数

;map
;map接受一个函数、一个或多个集合作为参数，返回一个序列作为结果，这个返回的序列是把这个函数应用到所有集合对应元素所得结果的一个序列。

;reduce
;归约：向一个集合应用一个函数二产生单个值的过程

;apply
;函数应用:给定一个函数，以及要传给这个函数的参数序列，然后来调用这个函数的过程

;partial
;偏函数：把函数的一部分参数传给一个函数，这样创建一个新的函数，这个函数需要的参数就是你没有传给那个函数的那些剩余函数
;预先为某个函数加上个别参数，等到函数正式被调用的时候把剩下的参数补上

;偏函数 VS 函数字面量
(#(filter string? %) ["a" 5 "b" 6])                         ;;("a" "b")

;函数字面量并不限制你去指定函数的开头几个参数
(#(filter % ["a" 5 "b" 6]) string?)                         ;;("a" "b")
(#(filter % ["a" 5 "b" 6]) number?)                         ;;(5 6)

;函数字面量强制指定要调用的函数的所有参数，而partial则部强调这一点
;(#(map *) [1 2 3] [4 5 6] [7 8 9]);;ArityException
(#(map * % %2 %3) [1 2 3] [4 5 6] [7 8 9])                  ;;(28 80 162)

(#(map * % %2 %3) [1 2 3] [4 5 6])                          ;;ArityException Wrong number of args (2) passed to:
(#(apply map * %&) [1 2 3] [4 5 6] [7 8 9])                 ;;(28 80 162)
(#(apply map * %&) [1 2 3])                                 ;;(1 2 3)

((partial map *) [1 2 3] [4 5 6] [7 8 9])                   ;;(28 80 162)


;函数功能的组合
;给定一个列表的数字，返回这些数字的总和的负数的字符串形式
(defn negated-sum-str
  [& numbers]
  (str (- (apply + numbers))))
(negated-sum-str 10 12 3.4)                                 ;;"-25.4"

;comp
;在clojure中，通过comp来实现函数组合
(def negated-sum-str2 (comp str - +))
(negated-sum-str 10 12 3.4)                                 ;;"-25.4"
;comp生成的函数的返回值是comp的第一个参数（函数）的返回值
;提供给comp的函数的返回值毕夏能够作为下一个函数的参数。
((comp + - str) 5 10)                                       ;;ClassCastException java.lang.String cannot be cast to java.lang.Number

;编写高阶函数，adder的返回值是一个函数
(defn adder
  [n]
  (fn [x] (+ x n)))

((adder 5) 18)                                              ;;23

(defn doubler
  [f]
  (fn [& args]
    (* 2 (apply f args))))
(def doubler-+ (doubler +))
(doubler-+ 1 2 3)                                           ;;12

;纯函数
;函数的副作用：副作用是指函数与外界环境的交互，不管是函数改变外部环境，还是依赖外部环境的一个值。
;纯函数：它不依赖于外部的数据源，也不会改变任何外部环境的状态，因而对于相同的参数，始终返回相同的结果
;纯函数的返回值完全由参数决定的。
;纯函数的结果是可以被缓存的，并且很容易并行化。只包含春函数的表达式被称为是引用透明的。意思就是，对应任何使用这种表达式的地方，你可以放心地把它用它的返回值代替，而不会改变代码的行为。
;下面的这些表达式都是等价的，因为这些表达式中使用的函数都是纯函数
(+ 1 2)
(- 10 7)
(count [-1 0 1])
;这些表达式出现的地方都可以直接把它们用它们的结果3来代替，而不会改变代码整体的结果。
;从实用的角度来说，这意味着魏蔓可以放心地缓存纯函数的返回值，这样下次再调用它的时候可以直接返回这个返回值二不用重写计算，这个技术叫做：内存化，当一个函数的调用成本很高时，这个技术就能帮上忙了
;memorize
(defn prime?
  [n]
  (cond
    (== 1 n) false
    (== 2 n) true
    (even? n) false
    :else (->> (range 3 (inc (Math/sqrt n)) 2)
               (filter #(zero? (rem n %)))
               empty?)))
(time (prime? 1125899906842679))
;;"Elapsed time: 3120.470361 msecs"
;;true
(let [m-prime? (memoize prime?)]
  (time (m-prime? 1125899906842679))
  (time (m-prime? 1125899906842679)))
;;"Elapsed time: 3097.219235 msecs"
;;"Elapsed time: 0.012368 msecs"
;;true

;有副作用的函数，就不是引用透明的了，所以也就不能内存化了。
(repeatedly 10 (partial rand-int 10))                       ;;(7 0 6 7 0 6 3 5 3 0)
(repeatedly 10 (partial (memoize rand-int) 10))             ;;(3 3 3 3 3 3 3 3 3 3)


