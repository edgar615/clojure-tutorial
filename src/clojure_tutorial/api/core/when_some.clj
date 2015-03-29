(ns clojure-tutorial.api.core.when-some)

;when-some
;(when-some bindings & body)
;bindings => binding-form test
;When test is not nil, evaluates body with binding-form bound to the value of test

;不懂

(when-some [x 1] [x :ok])                                   ;;[1 :ok]
(when-some [x nil] [x :ok])                                 ;;nil
(when-some [x :ok] [x nil])                                 ;;