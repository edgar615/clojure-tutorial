(ns
  ^{:author edgar}
  clojure_tutorial.api.core.make-array)

;make-array
;    (make-array type len)
;(make-array type dim & more-dims)
;Creates and returns an array of instances of the specified class of the specified dimension(s).
; Note that a class object is required.
; Class objects can be obtained by using their imported or fully-qualified name.
; Class objects for the primitive types can be obtained using, e.g., Integer/TYPE.

; Types are defined in clojure/genclass.clj:
;    Boolean/TYPE
;    Character/TYPE
;    Byte/TYPE
;    Short/TYPE
;    Integer/TYPE
;    Long/TYPE
;    Float/TYPE
;    Double/TYPE
;    Void/TYPE

(make-array Integer/TYPE 3)
;;#<int[] [I@42039326>

(pprint (make-array Double/TYPE 3))
;;[0.0, 0.0, 0.0]
(pprint (make-array Integer/TYPE 2 3))
;;[[0, 0, 0], [0, 0, 0]]

(def ar (make-array Thread 3))

(pprint ar)
;;[nil, nil, nil]

(type ar)
;;[Ljava.lang.Thread;

;; make-array 创建任意大小或维度的新空数组
(def arr (make-array String 5 5))
(aget arr 0 0)
;; nil
