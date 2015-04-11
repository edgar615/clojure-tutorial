(ns clojure-tutorial.api.core.assoc)
;assoc
;    (assoc map key val)
; (assoc map key val & kvs)

;assoc[iate]. When applied to a map, returns a new map of the same (hashed/sorted) type, that contains the mapping of key(s) to val(s).
; When applied to a vector, returns a new vector that contains val at index. Note - index must be <= (count vector).

(assoc {} :key1 "value" :key2 "another value")              ;{:key2 "another value", :key1 "value"}


;; Here we see an overwrite by a second entry with the same key
(assoc {:key1 "old value1" :key2 "value2"}
  :key1 "value1" :key3 "value3")                            ;;{:key3 "value3", :key2 "value2", :key1 "value1"}

;; We see a nil being treated as an empty map
(assoc nil :key 4)                                          ;;{:key 4}

;get,assoc也支持对vector进行操作，map和vector都可以看作是关系型集合，只不过对于vector来说，这个“关系”中的key的数组下标
;; 'assoc' being used on an array, the key is the index.
(assoc [1 2 3] 0 10)                                        ;;[10 2 3]
(assoc [1 2 3] 3 10)                                        ;;[1 2 3 10]
;; but if the index does not exist, it is not added automagically
(assoc [1 2 3] 4 10)                                        ;;IndexOutOfBoundsException