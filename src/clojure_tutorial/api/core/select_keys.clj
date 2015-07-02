(ns clojure-tutorial.api.core.select-keys)

;select-keys
;(select-keys map keyseq)
;Returns a map containing only those entries in map whose key is in keys

(select-keys {:a 1 :b 2} [:a])
;;{:a 1}

(select-keys {:a 1 :b 2} [:a :c])
;;{:a 1}

(select-keys {:a 1 :b 2 :c 3} [:a :c])
;;{:c 3, :a 1}
