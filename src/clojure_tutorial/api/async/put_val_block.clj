;; >!!
;; (>!! port val)
;; puts a val into port. nil values are not allowed. Will block if no
;; buffer space is available. Returns nil.

(require '[clojure.core.async :as async :refer :all])
(def c (chan 1))


(>!! c "Blocking - not in go-block")

(<!! c)
;; "Blocking - not in go-block"
