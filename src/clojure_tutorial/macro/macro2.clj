(ns clojure-tutorial.macro.macro2)

(defmacro my-when
  "my-when"
  {:added "1.0"}
  [test & body]
  (list 'if test (cons 'do body)))

(my-when (= 2 (+ 1 1))
         (println "You got")
         (println " the touch")
         (println))

(macroexpand-1 '(my-when (= 2 (+ 1 1))
                        (println "You got")
                        (println " the touch")
                        (println)))