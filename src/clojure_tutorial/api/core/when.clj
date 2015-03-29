(ns clojure-tutorial.api.core.when)

;when
;(when test & body)
;Evaluates test. If logical true, evaluates body in an implicit do.

(when (= 1 1) true)                                         ;;true
(when (not (= 1 1)) true)                                   ;;nil

(when true (println "Hello world") "Return value")
;;Hello world
;;"Return value"

(when "test" (println "Hello world") "Return value")
;;Hello world
;;"Return value"

(when false (println "Hello world") "Return value")         ;;nil