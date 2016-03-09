;; <!

;; (<! port)

;; takes a val from port. Must be called inside a (go ...) block. Will
;; return nil if closed. Will park if nothing is available.

(require '[clojure.core.async :as async :refer :all])
(def c (chan 1))

(go-loop [data (<! c)]
         (println "Waited for ==> " data)
         (println "No recur. Won't print again"))

(put! c "Example Async Data")

;; 控制台会打印
;; Waited for ==>  Example Async Data
;; No recur. Won't print again

(put! c "Example Async Data")
;; 控制台不再打印

(go-loop []
         (let [data (<! c)]
         (println "Waited for ==> " data))
         (recur))

(put! c "Example Async Data")
;; 控制台打印
;; Waited for ==>  Example Async Data

(put! c "Example Async Data")
;; 控制台打印
;; Waited for ==>  Example Async Data


;; Clojure 1.7
;; (let [c (chan 1 (filter pos?))]
;;   (go-loop []
;;            (let [nums (<! c)]
;;              (println nums))
;;            (recur))
;;   (doseq [n (range -10 10)]
;;     (put! c n)))
