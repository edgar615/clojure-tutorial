;into
;(into to from)

;Returns a new coll consisting of to-coll with all of the items of from-coll conjoined.

; Maps can be constructed from a sequence of 2-vectors or a sequence of maps
(into (hash-map) [[:a 1] [:b 2] [:c 3]])                    ;;{:a 1, :b 2, :c 3}
(into (hash-map) [{:a 1} {:b 2} {:c 3}])                    ;;{:a 1, :b 2, :c 3}

; When maps are the input source, they convert into an unordered sequence of key-value pairs, encoded as 2-vectors
(into [] {1 2 3 4})                                         ;;[[1 2] [3 4]]

(into () '(1 2 3))                                          ;;(3 2 1)

(into [] (set [:a :a]))
;; => [:a]

(into {} (seq {:a 1 :b 2}))
;; => {:a 1, :b 2}

(map identity {:sunlight-reaction "Glitter!"})
;; => ([:sunlight-reaction "Glitter!"])
(into {} (map identity {:sunlight-reaction "Glitter!"}))
;; => {:sunlight-reaction "Glitter!"}

(map identity [:garlic :sesame-oil :fried-eggs])
;; => (:garlic :sesame-oil :fried-eggs)

(into [] (map identity [:garlic :sesame-oil :fried-eggs]))
;; => [:garlic :sesame-oil :fried-eggs]

(into #{} (map identity [:garlic-clove :garlic-clove]))
;; => #{:garlic-clove}

(into {:favorite-animal "kitty"} {:least-favorite-smell "dog"
                                  :relationship-with-teenager "creepy"})
;; => {:favorite-animal "kitty", :least-favorite-smell "dog", :relationship-with-teenager "creepy"}

;; If into were asked to describe its strengths at a job interview,
;; it would say, “I’m great at taking two collections and adding all the elements from the second to the first.”
