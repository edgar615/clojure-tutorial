;drop-while
;(drop-while pred coll)
;Returns a lazy sequence of the items in coll starting from the first item for which (pred item) returns logical false.

(drop-while neg? [-1 -2 -6 -7 1 2 3 4 -5 -6 0 1])           ;;(1 2 3 4 -5 -6 0 1)

;; Their cousins take-while and drop-while are a bit more interesting.
;; Each takes a predicate function (a function whose return value is evaluated for truth or falsity)
;; to determine when it should stop taking or dropping.
;; Suppose, for example, that you had a vector representing entries in your “food” journal.
;; Each entry has the year, month, day, and what you ate. To preserve space, we’ll only include a few entries:


(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while
 #(< (:month %) 3)
 food-journal)
;; => ({:month 1, :day 1, :human 5.3, :critter 2.3}
;; {:month 1, :day 2, :human 5.1, :critter 2.0}
;; {:month 2, :day 1, :human 4.9, :critter 2.1}
;; {:month 2, :day 2, :human 5.0, :critter 2.5})

(drop-while
 #(< (:month %) 3)
 food-journal)
;; => ({:month 3, :day 1, :human 4.2, :critter 3.3}
;; {:month 3, :day 2, :human 4.0, :critter 3.8}
;; {:month 4, :day 1, :human 3.7, :critter 3.9}
;; :month 4, :day 2, :human 3.7, :critter 3.6})

(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))
;; => ({:month 2, :day 1, :human 4.9, :critter 2.1}
;; {:month 2, :day 2, :human 5.0, :critter 2.5}
;; {:month 3, :day 1, :human 4.2, :critter 3.3}
;; {:month 3, :day 2, :human 4.0, :critter 3.8})
