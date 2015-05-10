(ns clojure-tutorial.api.core.some)

;some
;(some pred coll)

;Returns the first logical true value of (pred x) for any x in coll,else nil.
; One common idiom is to use a set as pred, for example this will return :fred if :fred is in the sequence, otherwise nil:(some #{:fred} coll)

;some函数在一个序列里面搜索第一个能够符合指定谓词的元素。

(some #{1 2 7} [0 2 4 5 6])                                 ;;2
(some #{1 3 7} [0 2 4 5 6])                                 ;;nil

(some #(= 5 %) [1 2 3 4 5])                                 ;;true
(some #(= 5 %) [6 7 8 9 10])                                ;;nil
