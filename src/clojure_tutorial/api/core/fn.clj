;; fn
;; (fn name? [params*] exprs*)
;; (fn name? ([params*] exprs*) +)

;; params => positional-params* , or positional-params* & next-param
;; positional-param => binding-form
;; next-param => binding-form
;; name => symbol
;;  Defines a function

(def add (fn [x y]
       (+ x y)))

(add 1 2)

;; Anonymous Functions
(map (fn [name] (str "Hi, " name))
     ["Darth Vader" "Mr. Magoo"])
;; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

((fn [x] (* 3 x)) 8)
;; => 24

;; Clojure also offers another, more compact way to create anonymous functions. Here’s what an anonymous function looks like:
(#(* % 3) 8)
;; => 24

(map #(str "Hi, " %)
     ["Darth Vader" "Mr. Magoo"])
;; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

;; If your anonymous function takes multiple arguments, you can distinguish them like this: %1, %2, %3, and so on.
;; % is equivalent to %1:
(#(str %1 " and " %2) "cornbread" "butter beans")
;; => "cornbread and butter beans"

;; You can also pass a rest parameter with %&:
(#(identity %&) 1 "blarg" :yip)
;; => (1 "blarg" :yip)

;; Returning Functions
(defn inc-marker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

((inc-marker 1) 2)
;; => 3


