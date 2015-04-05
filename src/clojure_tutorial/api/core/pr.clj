(ns clojure-tutorial.api.core.pr)

;pr
;    (pr)(pr x)(pr x & more)

;Prints the object(s) to the output stream that is the current value of *out*.
; Prints the object(s), separated by spaces if there is more than one.
; By default, pr and prn print in a way that objects can be read by the reader

(pr "foo")
;"foo"nil

(pr \c)
;\cnil

(pr {:foo "hello" :bar 34.5})
;{:bar 34.5, :foo "hello"}nil

;; Difference between pr and print
(pr ['a :b "\n" \space "c"])
;[a :b "\n" \space "c"]nil

(print ['a :b "\n" \space "c"])
;[a :b
; c]nil
