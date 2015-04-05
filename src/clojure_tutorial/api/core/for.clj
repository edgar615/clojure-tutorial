(ns clojure-tutorial.api.core.for)

;for
;(for seq-exprs body-expr)

;List comprehension. Takes a vector of one or more binding-form/collection-expr pairs, each followed by zero or more modifiers,
;and yields a lazy sequence of evaluations of expr.Collections are iterated in a nested fashion, rightmost fastest,
;and nested coll-exprs can refer to bindings created in prior binding-forms.
; Supported modifiers are: :let [binding-form expr ...],:while test, :when test.

(take 10 (for [x (range 1000) y (range 100) :while (< y x)] [x y]))
;;([1 0] [2 0] [2 1] [3 0] [3 1] [3 2] [4 0] [4 1] [4 2] [4 3])

(def digits (seq [1 2 3]))
(for [x digits y digits] (* x y))                           ;;(1 2 3 2 4 6 3 6 9)

(for [x [:a :b :c] y [1 2 3]] [x y])                        ;;([:a 1] [:a 2] [:a 3] [:b 1] [:b 2] [:b 3] [:c 1] [:c 2] [:c 3])

(for [num (range 10)]
  (str num))                                                ;;("0" "1" "2" "3" "4" "5" "6" "7" "8" "9")

(for [alpha "abcdefg"
      num (range 1 5)]
  (str alpha num))                                          ;;("a1" "a2" "a3" "a4" "b1" "b2" "b3" "b4" "c1" "c2" "c3" "c4" "d1" "d2" "d3" "d4" "e1" "e2" "e3" "e4" "f1" "f2" "f3" "f4" "g1" "g2" "g3" "g4")

(for [x [1 2 3 4 5]
      :let [y (* x 3)]
      :when (even? y)]
  y)                                                        ;;(6 12)

(defn map-map
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))
(map-map inc (hash-map :z 5 :c 6 :a 8))                     ;;{:z 6, :c 7, :a 9}
(map-map inc (sorted-map :z 5 :c 6 :a 8))                   ;;{:a 9, :c 7, :z 6}


