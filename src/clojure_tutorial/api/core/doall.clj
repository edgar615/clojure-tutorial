(ns clojure-tutorial.api.core.doall)

;doall
;    (doall coll)
; (doall n coll)

;When lazy sequences are produced via functions that have side effects, any effects other than those needed to produce the first
;element in the seq do not occur until the seq is consumed. doall can be used to force any effects. Walks through the successive nexts of the seq,
;retains the head and returns it, thus causing the entire seq to reside in memory at one time.

(def foo (map println [1 2 3]))
;;#'user/foo

(def foo (doall (map println [1 2 3])))
;1
;2
;3
;#'user/foo

(doall (map println [1 2 3]))
;1
;2
;3
;(nil nil nil)
