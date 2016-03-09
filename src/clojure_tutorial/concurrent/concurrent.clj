;; (future (Thread/sleep 4000)
;;         (println "I'll print after 4 seconds"))

(let [result (future
               (println "this prints once")
               (+ 1 1))]
  (println "deref:" (deref result))
  (println "@:" @result))

;; this prints once
;; deref: 2
;; @: 2
;; nil


(let [result (future (Thread/sleep 3000)
                     (+ 1 1))]
  (println "The result is: " @result)
  (println "It will be at least 3 seconds before I print"))

;; The result is:  2
;; It will be at least 3 seconds before I print
;; nil


;; Sometimes you want to place a time limit on how long to wait for a future.
;; To do that, you can pass deref a number of milliseconds to wait along with the value to return if the deref times out:

;; This code tells deref to return the value 5 if the future doesn’t return a value within 10 milliseconds.
(deref (future (Thread/sleep 1000) 0) 10 5)
;; => 5

;; Finally, you can interrogate a future using realized? to see if it’s done running:
(realized? (future (Thread/sleep 1000)))
;; => false

(let [f (future)]
  @f
  (realized? f))
;; => true
