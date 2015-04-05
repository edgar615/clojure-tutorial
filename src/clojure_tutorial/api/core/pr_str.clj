(ns clojure-tutorial.api.core.pr-str)

;pr-str
;(pr-str & xs)
;pr to a string, returning it

(pr-str [1 2 3 4 5])                                        ;;"[1 2 3 4 5]"
(read-string (pr-str [1 2 3 4 5]))                          ;;[1 2 3 4 5]
(pr-str [ "hello" :a 1 (list :b 2) \c {:d 4} #{5 6 7} ])    ;;"[\"hello\" :a 1 (:b 2) \\c {:d 4} #{7 6 5}]"
