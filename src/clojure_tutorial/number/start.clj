(class (inc (Integer. 5)))
;;java.lang.Long

;数字操作的语义在不同类型之间以及操作不同类型的数字时都是一致的。
(dec 1)
;;0
(dec 1.0)
;;0.0
(dec 1N)
;;0N
(dec 1M)
;;0M
(dec 5/4)
;;1/4

;可以自由地在一个操作里混合使用不同类型的数字
(* 3 0.08 1/4 6N 1.2M)
;;0.432
(< 1 1.6 7/3 9N 14e9000M)
;;true

;有理数
;有理数可以表示为两个整数的分数的数字的集合
;1/3和3/5都是有理数

(+ 0.1 0.1 0.1)
;;0.30000000000000004
(+ 1/10 1/10 1/10)
;;3/10

(+ 7/10 1/10 1/10 1/10)
;;1N

;;比例数可以明确地强制转换为浮点表示
(double 1/3)
;;0.3333333333333333

;而浮点数也可以用rationalize函数转换为一个比例数
(rationalize 0.45)
;;9/20

;数字传播规则
;当一个算术操作涉及不同类型的数字时，操作返回值的类型由一个固定的层级决定。
;每种数字类型传播的程度各不相同，操作的参数传播度最高的一个决定返回值的类型。
; long -> BigInt -> Rationals -> BigDecimal -> double
;每个数字操作都必须返回某个具体类型的值，并且当操作涉及不同类型的参数时，
;必须定义一个方法来选取操作的类型。
;Clojure定义的具体层级是有顺序的，层级的实现永远不会让返回值的类型强制是“有损的”，
;例如米格long可以强制为big int或rationals或decimal而不影响语义，相反却不成立
;;操作于同一类型的参数返回那个类型的结果
(+ 1 1)
;;2
;;操作涉及long和double总会返回一个double类型
(+ 1 1.5)
;;2.5
;;操作涉及long和BigInteger综合返回一个BigInteger类型
(+ 1 1N)
;;2N
;;操作涉及BigDecimal和BigInteger总会返回一个BigDecimal类型
(+ 1.1M 1N)
;;2.1M

;这里唯一的问题是任何涉及double的都会返回double类型，即使double不能正确地表示其他数值表示的全部范围。
;这是因为：
;1.double定义了一些特别值，这些值不能表示为BigDecimal(特别是Infinity和NaN两个值)
;2.double是唯一一个天生就不精确的表示——一个涉及不精确数数字的操作返回一个暗示精确但实际不精确的类型值是有问题的

;类型传播的概念扩展到Clojure提供的算术操作以外。
;算术操作只是函数，因而同样的规则应用与你写的能接受数字为参数、用Clojure的操作符实现的函数。
(defn squares-sum
  [& vals]
  (reduce + (map * vals vals)))

(squares-sum 1 4 10)
;;117

(squares-sum 1 4 10 20.5)
;;537.25
(squares-sum 1 4 10 9N)
;;198N
(squares-sum 1 4 10 9N 5.6M)
;;229.36M
(squares-sum 1 4 10 25/2)
;;1093/4

;java的BigInteger有点问题，它的.hashCode实现与Long的不一致
(.hashCode (BigInteger. "6948736584"))
;;-1641197977
(.hashCode (Long. 6948736584))
;;-1641198007

;Clojure的基本数字操作符在整数溢出时会抛出异常，不会默默地“环绕”值
(def k Long/MAX_VALUE)
;(inc k)
;;java.lang.ArithmeticException: integer overflow

;明确使用任意精度的数值
(inc (bigint k))
;;9223372036854775808N
(* 100 (bigdec Double/MAX_VALUE))
;;1.797693134862315700E+310M

;整数字面量在超过64比特long类型的限度时会自动提升为BigInt类型

;如果在实现一种计算或算法，只是有可能超过long整数的限度，而类型传播不足以保证正确结果时，
;; 可以使用Clojure里带撇号的自动提升变体的算术操作符来自动地提升long类型的结果为BigInt类型，否则可能溢出
(inc' k)
;;9223372036854775808N
;; 不过这些操作符只在必要时提升结果，这保证了结果在64比特long类型范围内时不会被提升
(inc' 1)
;; 2
(inc' (dec' Long/MAX_VALUE))
;; 9223372036854775807

;; 带撇号的变体可用于Clojure里可能导致整数溢出或向下溢出的所有数字操作符：inc' dec' +' -' 和*'

;; 无检查的操作
;; 溢出和向下溢出是指对整数的操作结果超出了整数的表示法所能支持的限度。
;; 所有的数学操作逗号检查溢出和向下溢出，并在必要时抛出异常
;; (dec Long/MIN_VALUE)

;; Clojure的所有操作符都有unchecked-*变体，这些操作符不进行溢出/向下溢出检查
(unchecked-dec Long/MIN_VALUE)
;; 9223372036854775807
(unchecked-multiply Long/MAX_VALUE 1000)
;; -1000

;; 这些变体有些冗长，另一种办法是用set!把*unchecked-math*设置成真值，再执行数学操作都不需要检查的顶层形式
(set! *unchecked-math* true)
(inc Long/MAX_VALUE)
(set! *unchecked-math* false)

;; 任意精度的小数操作的刻度和取整模式
;; 宏with-precision
(/ 22M 7)
;; java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
(with-precision 10 (/ 22M 7))
;; 3.142857143M
(with-precision 10 :rounding FLOOR
  (/ 22M 7))
;; 3.142857142M

;; 相等与等值
;; 对象相同
;; 对象相同，Clojure是用identical?实现的，用来确定两个（或多个）对象完全是同一实例
