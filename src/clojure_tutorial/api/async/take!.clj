;; take!
;;     (take! port fn1)
;; (take! port fn1 on-caller?)

;; Asynchronously takes a val from port, passing to fn1. Will pass nil
;;  if closed. If on-caller? (default true) is true, and value is
;;  immediately available, will call fn1 on calling thread.
;;  Returns nil.

(require '[clojure.core.async :as async :refer :all])

(def c (chan 1))

(take! c
       (fn [x]
         (println "Clojure callback value " x)))

(put! c "XYZ") ;; return true unless the channel is closed.
;; true

;; 控制台打印
;; Clojure callback value  XYZ

(put! c "XYZ")
;; true

;; 控制台不打印
