;; admix
;; (admix mix ch)
;; Adds ch as an input to the mix

(require '[clojure.core.async :as async :refer :all])

(def ch-out (chan))

(def mix-out (mix ch-out))

(def ch-example1 (chan))

(def ch-example2 (chan))

(admix mix-out ch-example1)

(admix mix-out ch-example2)

(put! ch-example1 "sent to chan 1")

(put! ch-example2 "sent to chan 2")

(<!! ch-out)
;; "sent to chan 1"

(<!! ch-out)
;; "sent to chan 2"
