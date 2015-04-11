(ns clojure-tutorial.api.core.get)

;get
;    (get map key)
; (get map key not-found)

;Returns the value mapped to key, not-found or nil if key not present.

(get {:a 1 :b 2} :b)                                        ;;2
(get {:a 1 :b 2} :z)                                        ;;nil
(get {:a 1 :b 2} :z 3)                                      ;;3

;get,assoc也支持对vector进行操作，map和vector都可以看作是关系型集合，只不过对于vector来说，这个“关系”中的key的数组下标
(get [1 2 3] 1)                                             ;;2
(get [1 2 3] 5)                                             ;;nil

;虽然set本身美柚键值对的语义，不过当我们用get来操作一个set的时候，它的返回值意味着set是它里面值到值本身的映射。
(get #{1 2 3} 2)                                            ;;2
(get #{1 2 3} 4)                                            ;;nil
(get #{1 2 3} 4 "not-found")                                ;;"not-found"

(get "Clojure" 3)                                           ;;\j
(get (java.util.HashMap.) "not-therr")                      ;;nil
(get (into-array [1 2 3]) 0)                                ;;1