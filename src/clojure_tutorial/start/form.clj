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

;map解构
;map解构对于下面的几种数据结构有效
;Clojure原生的hash-map、array-map，以及记录类型
;任何实现了java.util.Map的对象
;get方法所支持的任何对象，比如：Clojure原生vector，字符串、数组
;map解构
(def m {:a 5 :b 6
        :c [7 8 9]
        :d {:e 10 :f 11}
        "foo" 88
        42 false})
(let [{a :a b :b} m]
  (+ a b))                                                  ;11
;可以在map解构中用做key的不止是关键字，可以是任何类型的值，比如字符串
(let [{f "foo"} m]
  (+ f 12))                                                 ;100
(let [{v 42} m]
  (if v 1 0))                                               ;0
;如果要进行map解构的是vector，字符串或者数组的话，那么解构的则是数字类型的数组下标。
(let [{x 3 y 8} [12 0 0 -18 44 6 0 0 1]]
  (+ x y))                                                  ;-17
;map解构也可以处理内嵌map
(let [{{e :e} :d} m]
  (* 2 e))                                                  ;20
;可以把顺序解构和map解构结合起来
(let [{[x _ y] :c} m]
  (+ x y))                                                  ;16
(def map-in-vector ["Edgar" {:birthday (java.util.Date. 73 1 6)}])
(let [[name {bd :birthday}] map-in-vector]
  (str name " was born on " bd))                            ;"Edgar was born on Tue Feb 06 00:00:00 CST 1973"
;保持被解构的集合
(let [{a :a :as original-map} m]
  (assoc original-map :name "Edgar"))                       ;{"foo" 88, :name "Edgar", :c [7 8 9], :b 6, :d {:e 10, :f 11}, 42 false, :a 5}
;默认值
;可以使用:or来提供一个默认的map，如果要解构的key在集合中没有的话，那么默认map中的值会作为默认值绑定到我们的解构符号上去
(let [{k :unknown x :a :or {k 50}} m]
  (+ k x))                                                  ;55
;上述代码等同于
(let [{k :unknown x :a} m
      k (or k 50)]
  (+ k x))
;:or能区分到底是美柚赋值，还是赋给的值就是逻辑false(nil或者false)
(let [{opt1 :option} {:option false}
      opt1 (or opt1 true)
      {opt2 :option :or {:opt2 true}} {:option false}]
  {:opt1 opt1 :opt2 opt2})
;绑定符号到map中同名关键字所对应的元素
(def chas {:name "Chas" :age 31 :location "Mass"})
(let [{name :name age :age location :location} chas]
  (format "%s is %s years old and lives in %s" name age location)) ;"Chas is 31 years old and lives in Mass"
;上述代码很冗长，这种情况下，Clojure提供了:keys、:strs和:syms来指定map中key的类型
;:keys表示key的类型是关键字，:strs表示key的类型是字符串，:syms表示key的类型是符号
;:keys
(let [{:keys [name age location]} chas]
  (format "%s is %s years old and lives in %s" name age location))
;:strs
(def brian {"name" "Brain" "age" 31 "location" "British"})
(let [{:strs [name age location]} brian]
  (format "%s is %s years old and lives in %s" name age location)) ;"Brain is 31 years old and lives in British"
;:syms
(def christophe {'name "Christophe" 'age 33 'location "Rhone"})
(let [{:syms [name age location]} christophe]
  (format "%s is %s years old and lives in %s" name age location)) ;"Christophe is 33 years old and lives in Rhone"
;对顺序集合的“剩余”部分使用map解构
(def user-info ["robert8990" 2011 :name "Bob" :city "Boston"])
(let [[username account-year & exra-info] user-info
      {:keys [name city]} (apply hash-map exra-info)]
  (format "%s is in %s" name city))                         ;"Bob is in Boston"
;Clojure可以直接使用map解构来解构集合的剩余部分——如果沈阳部分的元素格式是偶数的话，顺序解构会把剩余的部分当做一个map来处理
(let [[username account-year & {:keys [name city]}] user-info]
  (format "%s is in %s" name city))                         ;"Bob is in Boston"

;定义函数：fn
(fn [x]
  (+ x 10))
;函数定义时的参数与调用函数时实际传递的参数之间的对应是通过参数位置来完成的：
((fn [x]
   (+ x 10)) 8)                                             ;18
;也可以定义接受多个参数的函数
((fn [x y z] (+ x y z)) 3 4 12)                             ;19
;函数还可以又多个参数列表
(def strange-adder (fn adder-self-refrence
                     ([x] (adder-self-refrence x 1))
                     ([x y] (+ x y))))
(strange-adder 10)                                          ;11
(strange-adder 10 50)                                       ;60
;defn
(defn strange-adder
  ([x] (strange-adder x 1))
  ([x y] (+ x y)))
(def redundant-adder (fn redundant-adder
                       [x y z]
                       (+ x y z)))
;等价于
(defn redundant-adder [x y z]
  (+ x y z))
;解构函数参数
;可变参函数
(defn concat-rest
  [x & rest]
  (apply str (butlast rest)))
(concat-rest 0 1 2 3 4)                                     ;123
;“剩余参数”列表可以像其他序列一样进行解构
(defn make-user
  [& [user-id]]
  {:user-id (or user-id
                (str (java.util.UUID/randomUUID)))})
(make-user)                                                 ;{:user-id "b4ad6a0e-36e2-4e1b-91c4-b7a822964073"}
(make-user "Edgar")                                         ;{:user-id "Edgar"}
;关键字参数,关键字参数是构建在let对于剩余参数的map解构的基础上的。
;定义一个接受很多参数的函数时通常又一些参数不是必选的，有一些参数可能又默认值，而且有时候魏蔓希望函数的使用者不必按照某个特定的顺序来传参
(defn make-user2
  [username & {:keys [email join-date] :or {join-date (java.util.Date.)}}]
  {:username username
   :join-date join-date
   :email email})
(make-user2 "Bobby")                                        ;{:username "Bobby", :join-date #inst "2015-03-26T13:52:50.037-00:00", :email nil}
(make-user2 "Bobby"
            :join-date (java.util.Date.)
            :email "edgar615@gmail.com")                    ;{:username "Bobby", :join-date #inst "2015-03-26T13:52:58.720-00:00", :email "edgar615@gmail.com"}
;前置条件和后置条件
;函数字面量，匿名函数
(fn [x y] (Math/pow x y))
#(Math/pow %1 %2)
(read-string "#(Math/pow %1 %2)")                           ;(fn* [p1__828# p2__829#] (Math/pow p1__828# p2__829#))
;函数字面量没有隐式地使用do
;普通的fn（以及由它引申出来的所有变种）把它的函数体放在一个隐式的do里面，因此我们可以定义下面的函数：
(fn [x y]
  (println (str x \^ y))
  (Math/pow x y))
;如果使用函数字面量，需要显示地使用一个do形式
#(do (println (str %1 \^ %2))
     (Math/pow %1 %2))
;因为很多函数字面量都只接受一个参数，所以可以简单地使用%来引用它的第一个函数，
#(Math/pow % %2)
;等价于
#(Math/pow %1 %2)
;可以定义不定参数的函数，并且通过%&来引用那些剩余函数
(fn [x & rest]
  (- x (apply + rest)))
;等价于
#(- % (apply + %&))
;注意fn可以嵌套使用，但是函数字面量不能嵌套使用

;条件判断if
;如果if的第一个表达式的值是逻辑true的话，那么整个if的值就是第二个表达式的值
;Clojure的条件判断把任何非nil或非false的值都判断为true
(if "hi" \t)                                                ;\t
(if 42 \t)                                                  ;\t
(if nil "unevaluated" \t)                                   ;\t
(if false "unevaluated" \t)                                 ;\t
(if (not true) \t)                                          ;nil

;true?、false?检查所给的参数是不是布尔值true和false，而不是逻辑上的true和false

;循环：loop和recur
;Clojure提供了好几个有用的循环函数，包括doseq和dotimes，它们抖森构建在recur的基础之上
;recur能够在不消耗堆栈空间的情况下把程序执行转到离本地上下文最近的loop头那里去，这个loop头可以是loop定义的，也可以是一个函数定义的。
(loop [x 5]
  (if (neg? x)
    x
    (recur (dec x))))                                       ;-1
;函数也可以建立loop头，如果是函数建立loop头的话，那么recur所带的值则会绑定到函数的参数上面去
(defn countdown
  [x]
  (if (zero? x)
    :blastoff!
    (do (println x)
        (recur (dec x)))))
(countdown 5)

;引用var：var
;命名一个var的符号求值成var所对应的值
(def x 5)
x                                                           ;5
;如果你想获得指向var本身的引用，而不是var的值，var这个特殊形式就是用来做这个的
(var x)                                                     ;#'user/x

;和java的互操作：.和new
;对象初始化
(java.util.ArrayList. 100)
;调用静态函数
(Math/pow 2 10)
;调用实例方法
(.substring "hello" 1 3)
;访问静态程爷变量
Integer/MAX_VALUE
;访问实例成员变量
;(.someField someObject)

;异常处理：try、throw

;状态修改：set!
;虽然Clojure强调使用不可变的数据结构和值，但是还是有一些场景中我们需要对一个状态进行改变
;最常见的情况是调用一个java对象的set方法或者任意一个改变对象状态的方法，而对于剩下的那些场景，Clojure提供了函数：set!,它可以
;1.设置那些没有根绑定的线程本地值
;2.设置一个java的字段
;3.设置由deftype定义的对象的可修改字段

;锁的原语：monitor-enter和monitor-exit
;这两个是Clojure提供的锁原语，用来同步每个java对象上都有的monitor
;通常，不应该直接使用这两个原语，因为Clojure提供了一个宏：locking，它能够保证进行更合适的锁获取和锁释放


