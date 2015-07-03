(ns clojure-tutorial.concurrent.var)

map
;;#<core$map clojure.core$map@2d579e74>
#'map
;;#'clojure.core/map
(var map)
;;#'clojure.core/map
@#'map
;;#<core$map clojure.core$map@2d579e74>

;定义var
;私有var

;一个私有var具有如下特性
;在其他命名空间里面只能通过全限定名称对它进行访问
;要访问它的值只能通过解引用

(def ^:private everything 42)

;等价于
(def ^{:private true} everything 42)

(ns other-namespace)

(refer 'user)
everything
;;CompilerException java.lang.RuntimeException: Unable to resolve symbol: everything in this context,
@#'user/everything
;;42

;可以通过defn-来定义一个私有函数，它的作用跟defn完全一样，只是会自动给函数加上一个^private元数据

;文档字符串
(def a
  "A sample value."
  5)
(defn b
  "A simple calculation using 'a'."
  [c]
  (+ a c))
(doc a)
;;-------------------------
;;user/a
;;A sample value.
;;nil

(doc b)
;;-------------------------
;;user/b
;;([c])
;;A simple calculation using 'a'.
;;nil

;文档字符串只是var上的另外一个元数据而已；def在背后帮助我们自动把文档字符串加到var的元数据map里面去了
(meta #'a)
;;{:ns #<Namespace user>, :name a, :file "NO_SOURCE_PATH", :column 1, :line 1, :doc "A sample value."}

;;可以显式添加文档
(def ^{:doc "A sample value."} a 5)
(doc a)
;;-------------------------
;;user/a
;;A sample value.
;;nil

(alter-meta! #'a assoc :doc "A dummy value.")
(doc a)
;;-------------------------
;;user/a
;;A dummy value.
;;nil

;常量
;利用def来定义一个顶级var
(def ^:const everything 42)

;;动态作用域
;一个var的作用域在定义它的形式之内
(let [a 1
      b 2]
  (println (+ a b))
  (let [b 3
        + -]
    (println (+ a b))))
;;3
;;-2

;var提供了一种叫做“动态作用域”的特性，每个var有一个根绑定，这是使用def及其变种定义var的时候赋上的值，
;同时也是一般情况下对它解引用所得到的值。但是如果把一个var定义成动态的，就可以利用binging在每个线程上覆盖这个根绑定的值。
(def ^:dynamic *max-value* 255)
(defn valid-value?
  [v]
  (<= v *max-value*))

(binding [*max-value* 500]
  (valid-value? 299))
;;true

;虽然*max-value*是在valid-value?中被使用的，但是可以在外层利用binding来修改它的值。
;不过这仅仅是对线程本地的值的修改;我们可以看到*max-value*在其他线程中的值还是原来的
(binding [*max-value* 500]
  (println (valid-value? 299))
  (doto (Thread. #(println "in other thread：" (valid-value? 299)))
    .start
    .join))
;;true
;;in other thread： false

(def ^:dynamic *var* :root)
(defn get-*var* [] *var*)

(binding [*var* :a]
  (binding [*var* :b]
    (binding [*var* :c]
      (get-*var*))))
;;:c

;动态作用域不仅可以把一个参数/配置从调用树的上层传到下层去，也能反过来让下层的函数通过这个动态作用域给上层的函数返回一些东西。

(defn http-get
  [url-string]
  (let [conn (-> url-string java.net.URL. .openConnection)
        response-code (.getResponseCode conn)]
    (if (== 404 response-code)
      [response-code]
      [response-code (-> conn .getInputStream slurp)])))

(http-get "http://www.baidu.com/bad-url")

(http-get "http://www.baidu.com")

;但是作为http-get的用户，这种方式迫使我们对它的每次调用都要处理返回码，即使根本对它不感兴趣。

;而利用动态作用域的话，建立一个动态绑定，这样只有在我们对HTTP返回码感兴趣的时候http-get才会传回这个值

(def ^:dynamic *response-code* nil)

(defn http-get
  [url-string]
  (let [conn (-> url-string java.net.URL. .openConnection)
        response-code (.getResponseCode conn)]
    (when (thread-bound? #'*response-code*)
      (set! *response-code* response-code))
    (when (not= 404 response-code) (-> conn .getInputStream slurp))))

(http-get "http://www.baidu.com")

*response-code*
;;nil

(binding [*response-code* nil]
  (let [content (http-get "http://www.baidu.com/bar-url")]
    (println "Response code was:" *response-code*)))
;;Response code was: 200

;动态作用域会通过clojure原生的并发形式进行传播
;动态作用域的线程本地本质是非常有用的——它使得一个特定线程的运行跟其他线程完全隔离。

;Clojure的动态var绑定可以在线程之间进行传播，这种机制叫做“绑定传播”——当使用agent(通过send和send-off)、future以及pmap和它的变种的时候
(binding [*max-value* 500]
  (println (valid-value? 299))
  @(future (valid-value? 299)))
;;true
;;true

;虽然valid-future?是在另外一个独立的线程上被调用的，但是future把这个动态作用域传播到了这个线程上面

;pmap支持绑定传播，但是一般的惰性序列是不支持的
(binding [*max-value* 500]
  (map valid-value? [299]))
;;(false)

;可以手动将这个动态作用域“传播”到惰性序列中计算实际用到这个动态var的地方
(map #(binding [*max-value* 500]
        (valid-value? %))
  [299])
;;(true)

;修改var的根绑定
;alter-var-root函数，它以这个var本身以及一个将要作用在var身上的函数作为参数
(def x 0)
(alter-var-root #'x inc)
;;1

;如果var本身是一个函数的话，就可以用这种机制来实现面向切面的编程。
(defn sqr [n]
  "Squares a number"
  (* n n))

(sqr 5)
;;25

(alter-var-root
  (var sqr)                     ; var to alter
  (fn [f]                       ; fn to apply to the var's value
    #(do (println "Squaring" %) ; returns a new fn wrapping old fn
       (f %))))

(sqr 5)
;;Squaring 5
;;25

(alter-var-root #'sqr
  (fn [f]
    #(do (println "Squaring" %)
       (f %))))

(sqr 5)
;;Squaring 5
;;Squaring 5
;;25


;前置声明
;在定义一个var的时候可以暂时不给它赋值，在这种情况下，称这个var是“未绑定”的，
;如果你对它进行解引用的话，它会返回一个“占位符”对象
(def j)
j
;;#<Unbound Unbound: #'user/j>