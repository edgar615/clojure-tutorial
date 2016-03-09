;; chan
;;     (chan)(chan buf-or-n)

;; Creates a channel with an optional buffer. If buf-or-n is a number,
;; will create and use a fixed buffer of that size.

(require '[clojure.core.async :as async :refer :all])
(def c (chan))
(def c (chan 1))
(def c (chan (buffer 2)))

;; Clojure 1.7
;; (def c (chan 1 (filter string?)))
