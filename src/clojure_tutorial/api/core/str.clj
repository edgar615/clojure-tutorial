(ns clojure-tutorial.api.core.str)

;str
;    (str)
;(str x)
;(str x & ys)

;With no args, returns the empty string. With one arg x, returns x.toString().
; (str nil) returns the empty string. With more than one arg, returns the concatenation of the str values of the args.

"some string"                                               ;;"some string"

(str)                                                       ;;""

(str nil)                                                   ;;""

(str 1)                                                     ;;"1"

(str 1 2 3)                                                 ;;"123"

(srt 1 'symol :keyword)                                     ;;"1symol:keyword"

(apply str [1 2 3])                                         ;;(apply str [1 2 3])

(str [1 2 3])                                               ;;"[1 2 3]"
