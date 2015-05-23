(ns clojure-tutorial.api.core.future?)

;future?
;(future? x)
;Returns true if x is a future

(def f (future (inc 1)))

(future? f)                                                 ;;true

(future? 1)                                                 ;;false