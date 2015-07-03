(ns
  ^{:author edgar}
  clojure_tutorial.api.core.alter-var-root)

;alter-var-root
;(alter-var-root v f & args)

;Atomically alters the root binding of var v by applying f to its current value plus any args

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