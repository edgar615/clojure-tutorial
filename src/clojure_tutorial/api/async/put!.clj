;; put!
;;     (put! port val)
;; (put! port val fn0)
;; (put! port val fn0 on-caller?)

;; Asynchronously puts a val into port, calling fn0 (if supplied) when
;;  complete. nil values are not allowed. Will throw if closed. If
;;  on-caller? (default true) is true, and the put is immediately
;;  accepted, will call fn0 on calling thread.  Returns nil.

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
