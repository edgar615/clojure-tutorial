(ns clojure-tutorial.api.core.empty)

;empty
;    (empty coll)

;Returns an empty collection of the same category as coll, or nil
;获取一个跟所提供集合类型一样的空集合，通过它我们可以不必知道一个集合的具体类型就能创建出一个同类型的集合

(empty '(1 2))                                              ;;()
(empty [1 2])                                               ;;[]
(empty {:a 1})                                              ;;{}
(empty #{1 2})                                              ;;#{}

;; returns nil when input is not supported or not a collection
(empty 1)                                                   ;;nil