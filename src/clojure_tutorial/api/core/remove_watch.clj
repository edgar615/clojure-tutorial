(ns clojure-tutorial.api.core.remove-watch)

;remove-watch
;(remove-watch reference key)
;Removes a watch (set by add-watch) from a reference

(defn echo-watch
  [key identity old new]
  (println key old "=>" new))
(def sarah (atom {:name "Sarah" :age 25}))
(add-watch sarah :echo echo-watch)
;;#<Atom@3942d14f: {:age 25, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 25, :name Sarah} => {:age 26, :name Sarah}
;;{:age 26, :name "Sarah"}
(add-watch sarah :echo2 echo-watch)
;;#<Atom@3942d14f: {:age 26, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 26, :name Sarah} => {:age 27, :name Sarah}
;;:echo2 {:age 26, :name Sarah} => {:age 27, :name Sarah}
;;{:age 27, :name "Sarah"}

;删除一个观察者
(remove-watch sarah :echo2)
;;#<Atom@3942d14f: {:age 27, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 27, :name Sarah} => {:age 28, :name Sarah}
;;{:age 28, :name "Sarah"}