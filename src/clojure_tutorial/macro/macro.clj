(defmacro backwards
  [form]
  (reverse form))

(backwards (" backwards" " am" "I" str))
;; => "I am backwards"

(def addition-list (list + 1 2))
(eval addition-list)
;; => 3

(eval (concat addition-list [10]))
;; => 13

(eval (list 'def 'lucky-number (concat addition-list [10])))
lucky-number
;; => 13


;; read

;; read-string takes a string as an argument and processes it using Clojure’s reader, returning a data structure:
(read-string "(+ 1 2 3)")
;; => (+ 1 2 3)

(list? (read-string "(+ 1 2 3)"))
;; => true

(conj (read-string "(+ 1 2 3)") "zagglewag")
;; => ("zagglewag" + 1 2 3)

(eval (read-string "(+ 1 2 3)"))
;; => 6

;; Macros give you a convenient way to manipulate lists before Clojure evaluates them.
;; Macros are a lot like functions: they take arguments and return a value, just like a function would.
;; They work on Clojure data structures, just like functions do. What makes them unique
;; and powerful is the way they fit in to the evaluation process.
;; They are executed in between the reader and the evaluator—so they can manipulate the data structures
;; that the reader spits out and transform with those data structures before passing them to the evaluator.

(defmacro ignore-last-operand
  [function-call]
  (butlast function-call))

(ignore-last-operand (+ 1 2 10))
;; => 3

(macroexpand '(ignore-last-operand (+ 1 2 10)))
;; => (+ 1 2)

(defmacro infix
  [infixed]
  (list (second infixed)
        (first infixed)
        (last infixed)))

(infix (1 + 2))
;; => 3

(macroexpand '(infix (1 + 2)))
