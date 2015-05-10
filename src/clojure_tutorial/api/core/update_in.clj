(ns clojure-tutorial.api.core.update-in)
;update-in
;(update-in m [k & ks] f & args)
;'Updates' a value in a nested associative structure, where ks is a sequence of keys and f is a function that will take the old value
;and any supplied args and return the new value, and returns a new nested structure.  If any levels do not exist, hash-maps will be created.

(def p {:name "James" :age 26})

(update-in p [:age] inc)                                    ;;{:age 27, :name "James"}
(update-in p [:age] + 10)                                   ;;{:age 36, :name "James"}
(update-in p [:age] - 10)                                   ;;{:age 16, :name "James"}

(def version1 {:name "Chas" :info {:age 31}})
(def version2 (update-in version1 [:info :age] + 3))
version1                                                    ;;{:name "Chas", :info {:age 31}}
version2                                                    ;;{:name "Chas", :info {:age 34}}
