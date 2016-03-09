;; >!
;; (>! port val)
;; puts a val into port. nil values are not allowed. Must be called
;; inside a (go ...) block. Will park if no buffer space is available.

(require '[clojure.core.async :as async :refer :all])

(let [c (chan 1)]
  (go (>! c 1)
      (println "Got => " (<! c))))

;; 控制台打印
;; Got =>  1
