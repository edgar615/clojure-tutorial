;comp
;    (comp)
;(comp f)
;(comp f g)
;(comp f g h)
;(comp f1 f2 f3 & fs)

;Takes a set of functions and returns a fn that is the composition of those fns.  The returned fn takes a variable number of args,
;applies the rightmost of fns to the args, the next fn (right-to-left) to the result, etc.

((comp str - +) 1 2 3 4 5.6)                                ;;"-15.6"

(def negative-quotient (comp - /))
(negative-quotient 8 3)                                     ;;-8/3

(def concat-and-reverse (comp (partial apply str) reverse str))
(concat-and-reverse "hello" "clojuredocs")                  ;;"scoderujolcolleh"

((comp inc *) 2 3)

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(def c-int (comp :intelligence :attributes))
(def c-str (comp :strength :attributes))
(def c-dex (comp :dexterity :attributes))
(c-int character)
;; => 10
(c-str character)
;; => 4
(c-dex character)
;; => 5

(defn spell-slots
  [char]
  (inc (inc (/ (c-int char) 2))))
(spell-slots character)
;; => 7

(def spell-slots-comp (comp inc inc #(/ % 2) c-int))
(spell-slots-comp character)
;; => 7

(defn twoh-comp
  [f g]
  (fn [& args]
    (f (apply g args))))
