(ns clojure-tutorial.api.core.slurp)

;slurp
;(slurp f & opts)

;Opens a reader on f and reads all its contents, returning a string.
; See clojure.java.io/reader for a complete list of supported arguments.

(slurp "/home/edgar/dev/lein")


(slurp "http://www.baidu.com")

(slurp "http://www.baidu.com" :encoding "UTF-8")