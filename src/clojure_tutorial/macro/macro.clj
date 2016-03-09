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

(defmacro infix2
  [operand1 op operand2]
  (list op operand1 operand2))
(infix2 1 + 2)

(defmacro and
  "Evaluates exprs one at a time, from left to right. If a form
  returns logical false (nil or false), and returns that value and
  doesn't evaluate any of the other expressions, otherwise it returns
  the value of the last expr. (and) returns true."
  {:added "1.0"}
  ([] true)
  ([x] x)
  ([x & next]
   `(let [and# ~x]
      (if and# (and ~@next) and#))))

(and)
(and 1)
(and 1 2 3)

;; Your first version of the macro might look like this, using the list function to create the list that Clojure should evaluate:
(defmacro my-print-whoopsie
  [expression]
  (list let [result expression]
        (list println result)
        result))
;; => clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Can't take value of a macro: #'clojure.core/let

(defmacro my-print
  [expression]
  (list 'let ['result expression]
        (list 'println 'result)
        'result))
(my-print "hello")
;; => hello


;; Simple Quoting
(quote (+ 1 2))
;; => (+ 1 2)
(quote +)
;; => +

'(+ 1 2)
;; => (+ 1 2)
'dr-jekyll-and-richard-simmons
;; => dr-jekyll-and-richard-simmons

(defmacro when_
  "Evaluates test. If logical true, evaluates body in an implicit do."
  {:added "1.0"}
  [test & body]
  (list 'if test (cons 'do body)))
(when_ (> 3 1) (str "hello" "macro"))
(macroexpand '(when_ (the-cows-come :home)
                (call me :pappy)
                (slap me :silly)))
;; => (if (the-cows-come :home) (do (call me :pappy) (slap me :silly)))

(defmacro unless
  "Inverted if"
  [test & branches]
  (conj (reverse branches) test 'if))

(macroexpand '(unless (done-been slapped? me)
                      (slap me :silly)
                      (say "I reckon that'll learn me")))
;; => (if (done-been slapped? me) (say "I reckon that'll learn me") (slap me :silly))


;; Syntax Quoting
'+
;; => +
'clojure.core/+
;; => clojure.core/+
`+
;; => clojure.core/+
'(+ 1 2)
;; => (+ 1 2)
`(+ 1 2)
;; => (clojure.core/+ 1 2)


;; The other difference between quoting and syntax quoting is that the latter allows you to unquote forms using the tilde, ~.
;; It’s kind of like kryptonite in that way: whenever Superman is around kryptonite, his powers disappear.
;; Whenever a tilde appears within a syntax-quoted form, the syntax quote’s power to return unevaluated, fully namespaced forms disappears.
`(+ 1 (inc 1))
;; => (clojure.core/+ 1 (clojure.core/inc 1))
`(+ 1 ~(inc 1))
;; => (clojure.core/+ 1 2)

(list '+ 1 (inc 1))
;; => (+ 1 2)
`(+ 1 ~(inc 1))
;; => (clojure.core/+ 1 2)

(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  (list 'do
        (list 'println
              "Great squid of Madrid, this is bad code:"
              (list 'quote bad))
        (list 'println
              "Sweet gorilla of Manila, this is good code:"
              (list 'quote good))))
(code-critic (1 + 1) (+ 1 1))
;; Great squid of Madrid, this is bad code: (1 + 1)
;; Sweet gorilla of Manila, this is good code: (+ 1 1)

(defmacro code-critic2
  [bad good]
  `(do
     (println "Great squid of Madrid, this is bad code:"
              (quote ~bad))
     (println "Great squid of Madrid, this is good code:"
              (quote ~good))))
(code-critic2 (1 + 1) (+ 1 1))

(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic
  [bad good]
  `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code:" bad)
       ~(criticize-code "Sweet sacred boa of Western and Eastern Samoa, this is good code:" good)))
