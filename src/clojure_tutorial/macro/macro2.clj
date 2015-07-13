(ns clojure-tutorial.macro.macro2)

(read-string "(+ 1 2 3 4 5)")
;;(+ 1 2 3 4 5)
(class (read-string "(+ 1 2 3 4 5)"))
;;clojure.lang.PersistentList

(eval (read-string "(+ 1 2 3 4 5)"))
;;15
(class (eval (read-string "(+ 1 2 3 4 5)")))
;;java.lang.Long

(let [expression (read-string "(+ 1 2 3 4 5)")]
  (cons (read-string "*")
    (rest expression)))
;;(* 1 2 3 4 5)

(eval *1);*1 holds the result of the previous REPL evaluation
;;120


(let [expression (+ 1 2 3 4 5)]
  (cons (read-string "*")
    (rest expression)))
;;IllegalArgumentException Don't know how to create ISeq from: java.lang.Long

(let [expression '(+ 1 2 3 4 5)]
  (cons (read-string "*")
    (rest expression)))
;;(* 1 2 3 4 5)

(defn print-with-asterisks [printable-argument]
  (print "*****")
  (print printable-argument)
  (println "*****"))

(print-with-asterisks "hi")
;;*****hi*****

(print-with-asterisks
  (do (println "in argument expression")
    "hi"))
;;in argument expression
;;*****hi*****

(quote 1)
;;1
(quote "hello")
;;"hello"
(quote :kthx)
;;:kthx
(quote kthx)
;;kthx

'(+ 1 2 3 4 5)
;;(+ 1 2 3 4 5)
'map
;;map

(let [expression '(+ 1 2 3 4 5)]
  (cons '*
    (rest expression)))
;;(* 1 2 3 4 5)

;宏
(defmacro when2
  {:added "1.0"}
  [test & body]
  (list 'if test (cons 'do body)))

(when2 (= 2 (+ 1 1))
  (print "You got")
  (print " the touch!")
  (println))
;;You got the touch!


(macroexpand-1 '(when2 (= 2 (+ 1 1))
                 (print "You got")
                 (print " the touch!")
                 (println)))
;;(if (= 2 (+ 1 1)) (do (print "You got") (print " the touch!") (println)))



;Advance
(defmacro assert2 [x]
  (when *assert*
    (list 'when-not x
      (list 'throw
        (list 'new 'AssertionError
          (list 'str "Assert failed: "
            (list 'pr-str (list 'quote x))))))))

(assert2 (= 1 2))
;;AssertionError Assert failed: (= 1 2)

(macroexpand '(assert2 (= 1 2)))
;;(if (= 1 2) nil (do (throw (new AssertionError (str "Assert failed: " (pr-str (quote (= 1 2))))))))

;语法引述和反引述
;引述
(def a 4)

'(1 2 3 a 5)
;;(1 2 3 a 5)
(list 1 2 3 a 5)
;;(1 2 3 4 5)

;语法引述
(def a 4)
`(1 2 3 ~a 5)
;;(1 2 3 4 5)


(defmacro assert2 [x]
  (when *assert*
    `(when-not ~x
       (throw (new AssertionError (str "Assert failed: " (pr-str '~x)))))))

`(1 2 3 '~a 5)
;;(1 2 3 (quote 4) 5)

`(1 2 3 (quote (clojure.core/unquote a)) 5)
;;(1 2 3 (quote 4) 5)

(def other-nums '(4 5 6 7))

`(1 2 3 ~other-nums 9 10)
;;(1 2 3 (4 5 6 7) 9 10)
`(1 2 3 ~@other-nums 9 10)
;;(1 2 3 4 5 6 7 9 10)


(defmacro squares
  [xs]
  (list 'map '#(* % %) xs))

(squares (range 10))
;;(0 1 4 9 16 25 36 49 64 81)