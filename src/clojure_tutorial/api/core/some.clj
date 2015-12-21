;some
;(some pred coll)

;Returns the first logical true value of (pred x) for any x in coll,else nil.
; One common idiom is to use a set as pred, for example this will return :fred if :fred is in the sequence, otherwise nil:(some #{:fred} coll)

;some函数在一个序列里面搜索第一个能够符合指定谓词的元素。
;; some函数的返回值依赖于pred函数的返回值

(some #{1 2 7} [0 2 4 5 6])                                 ;;2
(some #{1 3 7} [0 2 4 5 6])                                 ;;nil

(some #(= 5 %) [1 2 3 4 5])                                 ;;true
(some #(= 5 %) [6 7 8 9 10])                                ;;nil

;; the first logical true value is returned, i.e. anything but nil and false
;; when return nil if its predicate is logical false.
(some #(when (even? %) %) '(1 2 3 4))
;; => 2

;;if you want to return the element the caused the predicate to return true
;;use the "and" function on the predicate and the argument:
(some #(and (even? %) %) [1 2 3 4])
;; => 2

;; a hash acts as a function returning nil when the
;; key is not present and the key value otherwise.
(some {2 "two" 3 "three"} [nil 3 2])
;; => three
(some {nil "nothing" 2 "two" 3 "three"} [nil 3 2])
;; => nothing

(first (filter even? [1 2 3 4]))
;; => 2

(some #(if (even? %) %) [1 2 3 4])
;; => 2

;; here we see sets being used as a predicates
;; the first member of the collection that appears in the set is returned
(#{2 3 4} 3)
(some #{2} (range 10))
;; => 2
(some #{6 2 4} (range 0 10))
;; => 2
(some #{6 2 4} (range 3 10))
;; => 4
(some #{200} (range 0 10))
;; => nil

;; if you have a case where the predicate arguments are fixed/known,
;; but the predicate function isn't:
;; coll can supply the predicate function instead of the predicate arguments
(defn ne [n1 n2] (not= n1 n2))
(some #(% 3 7) (list ne))
;; => true
(some #(% 3 3) (list ne))
;; => nil

(defn sumlt [limit n1 n2] (> limit (+ n1 n2)))
(some #(% 3 7) (list ne #(sumlt 10 %1 %2)))
;; => true
(some #(% 3 3) (list ne #(sumlt 10 %1 %2)))
;; => true
(some #(% 7 7) (list ne #(sumlt 10 %1 %2)))
;; => nil
;; same, but one of the functions returns a value instead a boolean.
(some #(% 3 7) (list ne (fn [n1 n2] (+ n1 n2))))
;; => true
(some #(% 3 3) (list ne (fn [n1 n2] (+ n1 n2))))
;; => 6
(some #(% 7 7) (list ne (fn [n1 n2] (+ n1 n2))))
;; => 14


(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(some #(> (:critter %) 5) food-journal)
;; => nil

(some #(> (:critter %) 3) food-journal)
;; => true

;; that the return value in the second example is true and not the actual entry that produced the true value.
;; The reason is that the anonymous function #(> (:critter %) 3) returns true or false. Here’s how you could return the entry:
(some #(and (> (:critter %) 3) %) food-journal)
;; => {:month 3, :day 1, :human 4.2, :critter 3.3}

