(ns clojure-tutorial.concurrent.start)

;delay
;delay是一种让代码延迟执行的机制，代码只会在显式地调用deref的时候执行

(def d (delay (println "Running...") :done!))

(deref d)
;;Running...
;;:done!

;deref是由接口clojure.lang.IDeref定义的，任何实现这个接口的对象会扮演一个值的容器的角色。
;你可以通过调用deref来对它解引用以获取它的值，或者使用语法糖@
;Clojure的很多实体是可以解引用的，包括delay,future,promise以及所有的引用类型:atom,ref,agent,var

;delay对它所包含的代码只执行一次，然后把返回值缓存起来。后面所有使用deref对它进行的访问抖森直接返回的，而不用再去执行它所包含的代码。

@d
;;:done!

;这意味着在第一次对delay进行解引用的时候可以多个线程同时解引用；所有的线程都会被阻塞住直到delay所包含的代码被求值（只求值一次！），之后所有的线程都可以访问这个值了

(defn get-document
  [id]
  {:url "http://www.baidu.com"
   :title "百度一下，你就知道"
   :mime "text/html"
   :content (delay (slurp "http://www.baidu.com"))})

(def d (get-document "some-id"))

d
;;{:url "http://www.baidu.com", :title "百度一下，你就知道", :mime "text/html", :content #<Delay@61222732: :pending>}

;使用realized?函数来检测delay的内容是否已经获取到了
(realized? (:content d))                                    ;;false

@(:content d)
(realized? (:content d))                                    ;;true

;realized?可以检测dealy的值有没有获取到，如果没有的话，可以选择不去获取这个值，因为这么做可能代价比较大，而你的代码可以暂时不用这个值
;realized?也可以用在future,promise以及惰性序列上

;future
;future会在另一个线程里面执行它所包含的代码
(def long-calculation (future (apply + (range 1e8))))
@long-calculation
;;4999999950000000

;如果在future的执行还没有完成的时候去解引用它的话，会阻塞当前的线程。

@(future (Thread/sleep 5000) :done)
;;:done

;future也会保持它所包含的代码的返回值，因此后续通过deref对future的访问会直接返回这个已经计算好的值

;在解引用一个future的时候，可以指定一个超时时间以及一个“超时值”
(deref (future (Thread/sleep 5000) :done!)
       1000
       :impatitent!)
;;:impatitent!

(defn get-document
  [id]
  {:url "http://www.baidu.com"
   :title "百度一下，你就知道"
   :mime "text/html"
   :content (future (slurp "http://www.baidu.com"))})

(def d (get-document "some-id"))

d


;promise
;一个promise也可以被解引用，解引用的时候可以传入一个超时的参数，解引用的时候如果这个promise还没有，那么解引用的代码会阻塞直到这个值准备好。
;但是promise在创建的时候并不会指定任何代码活摘函数来最终产生出它们的值
(def p (promise))
;promise开始是一个空容器，当程序运行的某个时间点，这个promise会被填充入一个值——通过deliver函数
(realized? p)                                               ;;false
(deliver p 42)                                              ;;#<core$promise$reify__6363@6f0ebf67: 42>
(realized? p)                                               ;;true
@p                                                          ;;42
;promise跟一个一次性的，单值的管道类似，

(def a (promise))
(def b (promise))
(def c (promise))
(future
  (deliver c (+ @a @b))
  (println "Delivery complete"))
;上述代码，c的值直到a和b的值都有了才会被传入

;简单的并行话
(defn phone-numbers
  "利用正则表达式从一个字符串中抽取除其中包含的电话号码"
  [string]
  (re-seq #"(\d{3})[\.-]?(\d{3})[\.-]?(\d{4})" string))

(phone-numbers " Sunil:617.555.2937, Betty: 508.555.2218")
;;(["617.555.2937" "617" "555" "2937"] ["508.555.2218" "508" "555" "2218"])
;创建１００个空字符串，每个字符串的大小为1M，电话号码则在每个字符串的末尾
(def files (repeat 100
                   (apply str
                          (concat (repeat 1000000 \space)
                                  "Sunil:617.555.2937, Betty: 508.555.2218"))))
(time (dorun (map phone-numbers files)))
;;"Elapsed time: 2754.66293 msecs"

;使用map的并行版本pmap
;pmap:以并行的方式用多个线程来把一个函数应用到一个序列的元素上去
(time (dorun (pmap phone-numbers files)))
;;"Elapsed time: 1460.286658 msecs"
;pmap内部只是简单地利用了一些future，future的个数就是机器的CPU核心的个数
;如果并行的操作本身不是很长，那将会使得线程之间的协调的开销相对于程序真正运行时间来说相当可观
(def files (repeat 100000
                   (apply str
                          (concat (repeat 1000 \space)
                                  "Sunil:617.555.2937, Betty: 508.555.2218"))))
(time (dorun (map phone-numbers files)))
;;"Elapsed time: 2924.500766 msecs"
(time (dorun (pmap phone-numbers files)))
;;"Elapsed time: 2729.365804 msecs"

;pcalls 接受任意数量无参函数作为参数，返回一个包含它们返回值的惰性序列
;pvalues　接受任意数量的表达式


;接受表达式作为参数，返回n个future
(defmacro futures
  [n & exprs]
  (vec (for [_ (range n)
             expr exprs]
         `(future ~expr))))
;wait-futures，始终返回nil并且阻塞REPL直到所有的future都实例化
(defmacro wait-futures
  [& args]
  `(doseq [f# (futures ~@args)]
     @f#))