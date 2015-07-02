(ns clojure-tutorial.api.core.io!)

;io!
;(io! & body)
;If an io! block occurs in a transaction, throws an IllegalStateException, else runs body in an implicit do.
; If the first expression in body is a literal string, will use that as the exception message.

;io!宏：当它被放在一个事务中执行，它会抛出一个错误
;因此如果你有一个函数可能会被误用在一个事务里面，那么可以把其中有副作用的部分用io!包起来。
(defn unsafe
  []
  (io! (println "writing to database...")))
(dosync (unsafe))
;;IllegalStateException I/O in transaction