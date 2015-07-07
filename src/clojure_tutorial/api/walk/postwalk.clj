(ns
  ^{:author edgar}
  clojure_tutorial.api.walk.postwalk)

;postwalk
;(postwalk f form)
;Performs a depth-first, post-order traversal of form.
; Calls f on each sub-form, uses f's return value in place of the original.
;Recognizes all Clojure data structures. Consumes seqs as with doall.

(use 'clojure.walk)

(let [counter (atom -1)]
  (postwalk (fn [x]
              [(swap! counter inc) x])
    {:a 1}))
;;3 {2 [[0 :a] [1 1]]}]

(let [counter (atom -1)]
  (postwalk (fn [x]
              [(swap! counter inc) x])
    {:a 1 :b 2}))
;;[6 {2 [[0 :b] [1 2]], 5 [[3 :a] [4 1]]}]

(let [counter (atom -1)]
  (postwalk (fn [x]
              [(swap! counter inc) x])
    [:a :b :c :d :e :f]))
;;[6 [[0 :a] [1 :b] [2 :c] [3 :d] [4 :e] [5 :f]]]