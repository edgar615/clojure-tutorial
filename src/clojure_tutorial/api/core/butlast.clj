(ns clojure-tutorial.api.core.butlast)
;butlast
;Return a seq of all but the last item in coll, in linear time
(butlast [1 2 3])                                           ;[1 2]
(butlast (butlast [1 2 3]))                                 ;[1]
