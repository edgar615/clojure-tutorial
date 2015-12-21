;map
;    (map f coll)
;(map f c1 c2)
;(map f c1 c2 c3)
;(map f c1 c2 c3 & colls)

;Returns a lazy sequence consisting of the result of applying f to the set of first items of each coll, followed by applying f to the set
;of second items in each coll, until any one of the colls is exhausted.  Any remaining items in other colls are ignored. Function f should accept number-of-colls arguments.

;map接受一个函数、一个或多个集合作为参数，返回一个序列作为结果，这个返回的序列是把这个函数应用到所有集合对应元素所得结果的一个序列。
;任何的map调用：(map f [a b c])和(f(a) f(b) f(c))是等价的；(map f [a b c] [x y z])和(f(a x) f(b y) f(c z))是等价的

;; sequence x1 x2 x3 x4
;;          |  |  |  |
;; map      f  f  f  f
;;          |  |  |  |
;; result   y1 y2 y3 y4

(map clojure.string/lower-case ["Java" "Imerative" "Weeping"
                                "Clojure" "Learning" "Peace"])
;;("java" "imerative" "weeping" "clojure" "learning" "peace")

(map * [1 2 3 4] [5 6 7 8])                                 ;;(5 12 21 32)

(map * [1 2 3 4] [4 5])                                     ;(4 10)

;map是Clojure中把一个顺序集合转换成另一个顺序集合的最基本的高阶函数。

(map inc [1 2 3 4 5])                                       ;;(2 3 4 5 6)

;; map can be used with multiple collections. Collections will be consumed
;; and passed to the mapping function in parallel:
(map + [1 2 3 4] [5 6 7 8])                                 ;;(6 8 10 12)

;; When map is passed more than one collection, the mapping function will
;; be applied until one of the collections runs out:
(map + [1 2 3] (iterate inc 1))                             ;;(2 4 6)

;; map is often used in conjunction with the # reader macro:
(map #(str "Hello, " % "!") ["Edgar" "Leona" "Jennifer"])
;;("Hello, Edgar!" "Hello, Leona!" "Hello, Jennifer!")

;; A useful idiom to pull "columns" out of a collection of collections.
;; Note, it is equivalent to:
;; user=> (map vector [:a :b :c] [:d :e :f] [:g :h :i])
(map vector [:a :b :c]
     [:d :e :f]
     [:g :h :i])                                            ;;([:a :d :g] [:b :e :h] [:c :f :i])

(map + '(1 2 3 4) '(5 6 7 8))                               ;;(6 8 10 12)
(map + [1 2 3 4] [5 6 7 8])                                 ;;[6 8 10 12]


(defn titleize
  [topic]
  (str topic " for the Brave and True"))

(map titleize ["Hamsters" "Ragnarok"])
;; => ("Hamsters for the Brave and True" "Ragnarok for the Brave and True")
(map titleize '("Empathy" "Decorating"))
;; => ("Empathy for the Brave and True" "Decorating for the Brave and True")
(map titleize #{"Elbows" "Soap Carving"})
;; => ("Elbows for the Brave and True" "Soap Carving for the Brave and True")
(map #(titleize (second %)) {:uncomfortable-thing "Winking"})
;; => ("Winking for the Brave and True")

(def human-consumption   [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human
   :critter critter})
(map unify-diet-data human-consumption critter-consumption)
;; => ({:human 8.1, :critter 0.0} {:human 7.3, :critter 0.2} {:human 6.6, :critter 0.3} {:human 5.0, :critter 1.1})


;; you can do with map is pass it a collection of functions. You could use this if you wanted to perform a set of calculations on different collections of numbers,
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))

(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [ 3 6 10])
;; => 19 3 19/3

(stats [80 1 44 13 6])
;; => (144 5 144/5)

(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])
(map :real identities)
;; => ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")
