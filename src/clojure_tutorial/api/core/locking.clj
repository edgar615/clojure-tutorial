(ns
  ^{:author edgar}
  clojure_tutorial.api.core.locking)

;locking
;(locking x & body)
;Executes exprs in an implicit do, while holding the monitor of x. Will release the monitor of x in all circumstances.
(def o (Object.))
(future (locking o
          (Thread/sleep 5000)
          (println "done1")))

(Thread/sleep 1000) ; give first instance 1 sec to acquire the lock
(locking o
  (Thread/sleep 1000)
  (println "done2"))
;;done1
;;done2
;;nil