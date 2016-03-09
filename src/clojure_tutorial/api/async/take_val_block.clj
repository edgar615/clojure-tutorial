;; <!!
;; (<!! port)
;; takes a val from port. Will return nil if closed. Will block
;; if nothing is available.

(require '[clojure.core.async :as async :refer :all])
(def c (chan 1))


(>!! c "Blocking - not in go-block")

(<!! c)
;; "Blocking - not in go-block"
