(ns clojure-tutorial.multimethod.start)

;一个多重方法是用defmulti形式创建的，而多重方法的实现是由defmethod形式提供的

;defmulti创建一个多重方法
;defmethod实现多重方法

;转发函数，传递给多重方法的参数被传递给这个函数，产生一个转发值,这个值用于为这些参数选择调用哪种方法
(defmulti fill
          "FIll a xml/html node (as per clojure.xml) with the provided value."
          (fn [node value] (:tag node)))

;:div就是一个转发值，当转发函数的返回值与这个转发值匹配时，所提供的方法实现被选中并被调用
(defmethod fill :div
  [node value]
  (assoc node :content [(str value)]))

(defmethod fill :input
  [node value]
  (assoc-in node [:attrs :value] (str value)))

(fill {:tag :div} "hello")
;;{:content ["hello"], :tag :div}

(fill {:tag :input} "hello")
;;{:attrs {:value "hello"}, :tag :input}

(fill {:span :input} "hello")
;;IllegalArgumentException No method in multimethod 'fill' for dispatch value: null


;一个多重方法——尽管是定义一个函数——并不显式指定它的参数个数，它支持它的转发函数支持的所有参数个数
;defmulti形式真正定义了一个新var，每个defmethod形式只是在“根”多重方法上注册上一个新的实现方法，defmethod并不重新定义任何var

;:default
(defmethod fill :default
  [node value]
  (assoc node :content [(str value)]))

(fill {:span :input} "hello")
;;{:content ["hello"], :span :input}

;defmulti接受选项，有一种方式可以指定默认转发值应是什么样的

;把默认方法值设置为nil
(defmulti fill
          "FIll a xml/html node (as per clojure.xml) with the provided value."
          (fn [node value] {:tag node})
          :default nil)

;默认实现
(defmethod fill nil
  [node value]
  (assoc-in node [:attrs :value] (str value)))

(defmethod fill :input
  [node value]
  (assoc-in node [:attrs :value] (str value)))

(defmethod fill :default
  [node value]
  (assoc-in node [:attrs :name] (str value)))

