(ns
  ^{:author edgar}
  clojure_tutorial.api.core.gensym)

;gensym
;    (gensym) (gensym prefix-string)
;Returns a new symbol with a unique name.
; If a prefix string is supplied, the name is prefix# where # is some unique number.
; If prefix is not supplied, the prefix is 'G__'.
;gensym函数返回一个保证唯一的符号。每次调用它都能返回一个新的符号
(gensym)
;;G__40
(gensym)
;;G__43

;gensym也可以接受一个参数，这个参数作为产生的符号的前缀
(gensym "sym")
;;sym46
(gensym "sym")
;;sym49