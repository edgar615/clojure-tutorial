(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0))
;; => "Elapsed time: 1000.258677 msecs"
;; => {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

(time (def mapped-details (map vampire-related-details (range 0 1000000))))
;; => "Elapsed time: 0.134577 msecs"

(time (first mapped-details))
;; => "Elapsed time: 32004.273499 msecs"
;; => {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

;; lazy seq elements need to be realized only once. Accessing the first element of mapped-details again takes almost no time:
(time (first mapped-details))
;; => "Elapsed time: 0.049377 msecs"
;; => {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}

(time (identify-vampire (range 0 1000000)))
;; => "Elapsed time: 32003.94445 msecs"
;; => {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}

;; Infinite Sequences 无限序列
;; Clojure comes with a few functions to create infinite sequences.
;; One easy way to create an infinite sequence is with repeat, which creates a sequence whose every member is the argument you pass:
(concat (take 8 (repeat "na")) ["Batman!"])
;; => ("na" "na" "na" "na" "na" "na" "na" "na" "Batman!")

;; You can also use repeatedly, which will call the provided function to generate each element in the sequence:
(take 3 (repeatedly (fn [] (rand-int 10))))
;; => (6 4 7)

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))
(take 10 (even-numbers))
;; => (0 2 4 6 8 10 12 14 16 18)
