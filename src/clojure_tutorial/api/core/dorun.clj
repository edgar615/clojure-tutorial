(ns clojure-tutorial.api.core.dorun)

;dorun
;    (dorun coll)
; (dorun n coll)

;When lazy sequences are produced via functions that have side effects, any effects other than those needed to produce the first
;element in the seq do not occur until the seq is consumed. dorun can be used to force any effects. Walks through the successive nexts of
;the seq, does not retain the head and returns nil.

(dorun 5 (repeatedly #(println "hi")))
;hi
;hi
;hi
;hi
;hi
;hi
;nil

(dorun (map #(println "hi" %) ["mum" "dad" "sister"]))
;hi mum
;hi dad
;hi sister
;nil
