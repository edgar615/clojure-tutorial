(ns clojure-tutorial.api.core.with-meta)

;meta
;(with-meta obj m)
;Returns an object of the same type and value as obj, with map m as its metadata.

;with-meta 把一个元数据完全替换成给定的元数据

(with-meta [1 2 3] {:my "meta"})                            ;;[1 2 3]
(meta (with-meta [1 2 3] {:my "meta"}))                     ;;{:my "meta"}
