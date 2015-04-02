(ns clojure-tutorial.api.core.into)

;into
;(into to from)

;Returns a new coll consisting of to-coll with all of the items of from-coll conjoined.

; Maps can be constructed from a sequence of 2-vectors or a sequence of maps
(into (hash-map) [[:a 1] [:b 2] [:c 3]])                    ;;{:a 1, :b 2, :c 3}
(into (hash-map) [{:a 1} {:b 2} {:c 3}])                    ;;{:a 1, :b 2, :c 3}

; When maps are the input source, they convert into an unordered sequence of key-value pairs, encoded as 2-vectors
(into [] {1 2 3 4})                                         ;;[[1 2] [3 4]]

(into () '(1 2 3))                                          ;;(3 2 1)