(ns clojure-tutorial.api.core.filter)

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

;remove的作用与filter相反