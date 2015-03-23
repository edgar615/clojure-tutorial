(ns clojure-tutorial.start.namespace)

;def只作用于当前命名空间

(def x 1)
;通过这个符号来访问这个var
x

;返回当前的命名空间
*ns*                                                        ;#<Namespace user>
(ns foo)                                                    ;nil，切换命名空间
*ns*                                                        ;#<Namespace foo>
user/x                                                      ;1
x                                                           ;RuntimeException

;java.lang这个包里面的类默认被引入到每个Clojure的命名空间中，所以可以不加包名直接访问这些java类；
;而对于那么美柚引入的类，则毕夏指定包名才能访问它们.
;任何指向java类的符号逗号被求值成那个java类
String
Integer
java.util.List
java.net.Socket

;每个命名空间都会默认引入Clojure的核心库clojure.core中的所有的var
