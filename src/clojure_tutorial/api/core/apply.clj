(ns clojure-tutorial.api.core.apply)

;apply
;(apply f args)
;(apply f x args)
;(apply f x y args)
;(apply f x y z args)
;(apply f a b c d & args)

;Applies fn f to the argument list formed by prepending intervening arguments to args.

(apply max [1 2 3])                                         ;;3
;等同于
(max 1 2 3)                                                 ;;3

(apply hash-map [:a 1 :b 2])                                ;;{:b 2, :a 1}

;; Here's an example that uses the optional second argument, args:
;; In this example, 'f' = 'map', 'args' = 'vector', and argseq = '[:a :b] [:c :d]',
;; making the above code equivalent to
(apply map vector [[:a :b] [:c :d]])
;等同于
(map vector [:a :b] [:c :d])                                ;;([:a :c] [:b :d])

(apply + '(1 2 3))                                          ;;6
;等同于
(+ 1 2 3)                                                   ;;6

(apply + 1 2 '(3 4))                                        ;;10
;等同于
(+ 1 2 3 4)

(map #(apply max %) [[1 2 3][4 5 6][7 8 9]])                ;;(3 6 9)