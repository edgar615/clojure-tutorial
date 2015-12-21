;filter
;(filter pred coll)

;Returns a lazy sequence of the items in coll for which (pred item) returns true. pred must be free of side-effects.

;返回一个满足指定谓词的惰性序列

(filter (fn [x]
          (= (count x) 1))
        ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""])     ;;("a" "b" "n" "f" "q")
(filter #(= (count %) 1) ["a" "aa" "b" "n" "f" "lisp" "clojure" "q" ""]) ;;("a" "b" "n" "f" "q")


(filter :age [{:age 21 :name "David"}
              {:gender :f :name "Suzanne"}
              {:name "Sara" :location "NYC"}])              ;;({:age 21, :name "David"})

(filter (comp (partial <= 25) :age) [{:age 21 :name "David"}
                                     {:gender :f :name "Suzanne" :age 20}
                                     {:name "Sara" :location "NYC" :age 30}]) ;;({:age 30, :name "Sara", :location "NYC"})


(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(filter #(< (:human %) 5)
        food-journal)
;; ({:month 2, :day 1, :human 4.9, :critter 2.1}
;; {:month 3, :day 1, :human 4.2, :critter 3.3}
;; {:month 3, :day 2, :human 4.0, :critter 3.8}
;; {:month 4, :day 1, :human 3.7, :critter 3.9}
;; {:month 4, :day 2, :human 3.7, :critter 3.6})

;remove的作用与filter相反
