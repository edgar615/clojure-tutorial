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
