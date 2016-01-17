;; read-string
;;     (read-string s)
;; (read-string opts s)

;; Reads one object from the string s. Optionally include reader
;; options, as specified in read.
;;  Note that read-string can execute code (controlled by *read-eval*),
;; and as such should be used only with trusted sources.
;;  For data structure interop use clojure.edn/read-string

(read-string "1.1")
;; => 1.1
(read-string "(+ 1 1)")
;; => (+ 1 1)

(eval (read-string "(+ 1 1 )"))
;; => 2

(+ 11 (read-string "22"))
;; => 33

(read-string ";  foo\n5")
;; => 5
(read-string "#^String x")
;; => x
(read-string "(1)")
;; => (1)

(read-string "(+ 1 2) (- 3 2)")
;; => (+ 1 2)

(read-string "@a")
;; => (clojure.core/deref a)
(read-string "::whatever-namespace-you-are-in")
;; => :user/whatever-namespace-you-are-in

;; in all the examples so far, there’s been a one-to-one relationship between the reader form and the corresponding data structures.
;; Here are more examples of simple reader forms that directly map to the data structures they represent:
;;     () A list reader form
;;     str A symbol reader form
;;     [1 2] A vector reader form containing two number reader forms
;;     {:sound "hoot"} A map reader form with a keyword reader form and string reader form


(read-string "#(+ 1 %)")
;; =>  (fn* [p1__4806#] (+ 1 p1__4806#))

(type (read-string "+"))
;; => clojure.lang.Symbol

(list (read-string "+") 1 2)
;; => (+ 1 2)
(eval (list (read-string "+") 1 2))
;; => 3
