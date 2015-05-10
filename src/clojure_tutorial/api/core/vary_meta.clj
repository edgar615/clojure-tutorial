(ns clojure-tutorial.api.core.vary-meta)

;vary-meta
;(vary-meta obj f & args)
;Returns an object of the same type and value as obj, with (apply f (meta obj) args) as its metadata.

;vary-meta通过给定的更新函数以及需要的参数对值当前的元数据进行更新

(meta (vary-meta 'foo assoc :a 1))                          ;;{:a 1}

(def wm (with-meta [1 2 3] {:my "meta"}))
wm                                                          ;;[1 2 3]
(meta wm)                                                   ;;{:my "meta"}

(def new-meta (vary-meta wm assoc :your "new meta"))
new-meta                                                    ;;[1 2 3]
(meta new-meta)                                             ;;{:your "new meta", :my "meta"}