(ns clojure-tutorial.coll.associative)

;关系型数据结构Associative接口所抽象的是把一个key和一个value关联起来的数据结构，它包括四个操作：
;assoc,向集合中添加一个新的key到value的映射
;dissoc，从集合中移出一个从指定key到value的映射
;get，从集合中找出指定key的value
;contain?，谓词，当这个集合包含指定的key的时候它返回true，否则返回false

;最正宗的关系型数据解构是map

(def m {:a 1, :b 2, :c 3})
(get m :b)                                                  ;;2
(get m :d)                                                  ;;nil
(get m :d "not-found")                                      ;;"not-found"
(assoc m :d 4)                                              ;;{:c 3, :b 2, :d 4, :a 1}
(dissoc m :b)                                               ;;{:c 3, :a 1}

;assoc和dissoc可以一次添加/去除多个键值对
(assoc m
  :x 4
  :y 5
  :z 6)                                                     ;;{:y 5, :z 6, :c 3, :b 2, :x 4, :a 1}
(dissoc m :a :c)                                            ;;{:b 2}

;get,assoc也支持对vector进行操作，map和vector都可以看作是关系型集合，只不过对于vector来说，这个“关系”中的key的数组下标
(def v [1 2 3])
(get v 1)                                                   ;;2
(get v 10)                                                  ;;nil
(get v 10 "not-found")                                      ;;"not-found"

(assoc v
  1 4
  0 -12)                                                    ;;[-12 4 3]

;get还可以操作set
(get #{1 2 3} 2)                                            ;;2
(get #{1 2 3} 4)                                            ;;nil
(get #{1 2 3} 4 "not-found")                                ;;"not-found"
;虽然set本身美柚键值对的语义，不过当我们用get来操作一个set的时候，它的返回值意味着set是它里面值到值本身的映射。
(when (get #{1 2 3} 2)
  (println "it contains '2'!"))                             ;;it contains '2'!

;contains? 谓词，检测集合中是否包含指定的key
(contains? [1 2 3] 0)                                       ;;true
(contains? {:a 5 :b 6} :b)                                  ;;true
(contains? {:a 5 :b 6} 42)                                  ;;false
(contains? #{1 2 3} 2)                                      ;;true

;get和contains?可以高效地操作veictor，map，set，java里面的map，字符串、数组
(get "Clojure" 3)                                           ;;\j
(contains? (java.util.HashMap.) "not-therr")                ;;false
(get (into-array [1 2 3]) 0)                                ;;1

;如果集合里面不包含某个key，并且调用的时候没有提供默认值，get会返回nil，但是这个key对应的value也可能正好就是nil
(get {:ethel nil} :lucy)                                    ;;nil
(get {:ethel nil} :ethel)                                   ;;nil
;为了避免这种问题，可以使用find，find和get类似，只是它返回的是一个键值对，如果不包含这个key会返回nil
(find {:ethel nil} :lucy)                                   ;;nil
(find {:ethel nil} :ethel)                                  ;;[:ethel nil]
;find可以很容易跟解构形式如if-let、when-let一起使用
(if-let [e (find {:a 5 :b 6} :a)]
  (format "found %s => %s" (key e) (val e))
  "not-found")                                              ;;"found :a => 5"
(if-let [[k v] (find {:a 5 :b 6} :a)]
  (format "found %s => %s" k v)
  "not-found")                                              ;;"found :a => 5"
