(ns clojure-tutorial.api.core.prn)

;prn
;(prn & more)
;Same as pr followed by (newline). Observes *flush-on-newline*

(prn "fred" 1)
;"fred" 1
;nil

(def items [ "hello" :a 1 (list :b 2) \c {:d 4} #{5 6 7} ])
; prn outputs items in a machine-readable format, such as in a source
; file. Note the double-quotes around the string "hello" and the escaped letter "c".
(prn items)
;["hello" :a 1 (:b 2) \c {:d 4} #{7 6 5}]
;nil

; println is for human-readable output, like a report. Note the lack of quotes around the string "hello", and the unescaped letter "c".
(println items)
;[hello :a 1 (:b 2) c {:d 4} #{7 6 5}]
;nil

; pr-str produces a string with escaped punctuation, so that println yields the same result as the original prn call.
(println (pr-str items))
;["hello" :a 1 (:b 2) \c {:d 4} #{7 6 5}]
;nil
