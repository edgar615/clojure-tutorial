;; to-array
;; (to-array coll)

;; Returns an array of Objects containing the contents of coll, which
;; can be any Collection.  Maps to java.util.Collection.toArray().

;; to-array 返回对象的数组

(to-array [1 2 3])
;; #<Object[] [Ljava.lang.Object;@13f6f7ef>

(to-array "hello world")
;; #<Object[] [Ljava.lang.Object;@669e8114>
(aget (to-array "hello world") 1)
;; \e
