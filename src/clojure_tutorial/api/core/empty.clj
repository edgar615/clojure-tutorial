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

;交换一个顺序集合中两个元素的位置
(defn swap-pairs
  [sequential]
  (into (empty sequential)
        (interleave
          (take-nth 2 (drop 1 sequential))
          (take-nth 2 sequential))))
(swap-pairs (apply list (range 10)))                        ;;(8 9 6 7 4 5 2 3 0 1)
(swap-pairs (apply vector (range 10)))                      ;;[1 0 3 2 5 4 7 6 9 8]

(defn map-map
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))
(map-map inc (hash-map :z 5 :c 6 :a 8))                     ;;{:z 6, :c 7, :a 9}
(map-map inc (sorted-map :z 5 :c 6 :a 8))                   ;;{:a 9, :c 7, :z 6}