(ns clojure-tutorial.api.core.ref-set)
;ref-set
;(ref-set ref val)
;Must be called in a transaction. Sets the value of ref. Returns val.

(def foo (ref {}))

(dosync
  (ref-set foo {:foo "bar"}))

@foo
;;{:foo "bar"}


(dosync
  (ref-set foo {:pro "bar"}))

@foo
;;{:pro "bar"}