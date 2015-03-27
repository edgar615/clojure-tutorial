(ns clojure-tutorial.api.core.when-not)

;when-not
;(when-not test & body)
;Evaluates test. If logical false, evaluates body in an implicit do.

(when-not (= 1 1) false)                                         ;;nil
(when-not (not (= 1 1)) false)                                   ;;false

(when-not true (println "Hello world") "Return value")      ;;nil