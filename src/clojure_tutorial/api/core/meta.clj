(ns clojure-tutorial.api.core.meta)

;meta
;(meta obj)

;Returns the metadata of obj, returns nil if there is no metadata.

(def a ^{:created (System/currentTimeMillis)}
[1 2 3])
(meta a)                                                    ;;{:created 1431247211121}

;如果要指定的元数据的key是关键字，而值是布尔值，可以使用一种简单的方式来指定
(meta ^:private [1 2 3])                                    ;;{:private true}
(meta ^:private ^:dynamic [1 2 3])                          ;;{:private true, :dynamic true}
