;; get-in
;; (get-in m ks)
;; (get-in m ks not-found)

;; Returns the value in a nested associative structure,
;; where ks is a sequence of keys. Returns nil if the key
;; is not present, or the not-found value if supplied.

(get-in {:a 0 :b {:c "ho hum"}} [:b :c])
;; "ho hum"

(def m {:username "sally"
               :profile {:name "Sally Clojurian"
                         :address {:city "Austin" :state "TX"}}})

(get-in m [:profile :address :city])
;; Austin

(get-in m [:profile :address :zip-code] "no zip code!")
;; "no zip code!"

;; Vectors are also associative:
(def v [[1 2 3]
               [4 5 6]
               [7 8 9]])
(get-in v [1 2])
;; 6

;; We can mix associative types:
(def mv {:username "jimmy"
                :pets [{:name "Rex"
                        :type :dog}
                       {:name "Sniffles"
                        :type :hamster}]})

(get-in mv [:pets 1])
;; {:name "Sniffles", :type :hamster}
