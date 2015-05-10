(ns clojure-tutorial.api.core.remove)

;remove
;(remove pred coll)
;Returns a lazy sequence of the items in coll for which (pred item) returns false. pred must be free of side-effects.

;remove的作用与filter相反

(remove :age [{:age 21 :name "David"}
              {:gender :f :name "Suzanne"}
              {:name "Sara" :location "NYC"}])              ;;({:name "Suzanne", :gender :f} {:name "Sara", :location "NYC"})
