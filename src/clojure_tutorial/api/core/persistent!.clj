(ns clojure-tutorial.api.core.persistent!)

;persistent!
;(persistent! coll)
;Returns a new, persistent version of the transient collection, in constant time.
; The transient collection cannot be used after this call, any such use will throw an exception.

;persistent!把一个易变集合变成持久性集合后会使那个易变集合不再可用

(def v [1 2])
(def tv (transient v))
(conj v 3)

(get tv 0)                                                  ;;1
(persistent! tv)                                            ;;[1 2]
(get tv 0)                                                  ;;IllegalAccessError Transient used after persistent! cal