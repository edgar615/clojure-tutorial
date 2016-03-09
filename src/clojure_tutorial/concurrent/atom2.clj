(def fred (atom {:cuddle-hunger-level 0
                 :percent-deteriorated 0}))
@fred
;; => {:percent-deteriorated 0, :cuddle-hunger-level 0}

(let [zombie-state @fred]
  (if (>= (:percent-deteriorated zombie-state) 50)
    (future (println (:percent-deteriorated zombie-state)))))

(swap! fred
       (fn [current-state]
         (merge-with + current-state {:cuddle-hunger-level 1})))
;; => {:cuddle-hunger-level 1, :percent-deteriorated 0}
@fred
;; => {:cuddle-hunger-level 1, :percent-deteriorated 0}


(let [num (atom 1)
      s1 @num]
  (swap! num inc)
  (println "State 1:" s1)
  (println "Current state:" @num))
;; => State 1: 1
;; => Current state: 2
