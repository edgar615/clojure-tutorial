(defn foo [x y z]
  (* x y z))

(foo 1 2 3)
;; 6

(defn too-enthusiastic
   "Return a cheer that might be a bit too enthusiastic"
   [name]
   (str "OH. MY. GOD! " name " YOU ARE MOST DEFINITELY LIKE THE BEST "
  "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"))

(too-enthusiastic "Zelda")
;; "OH. MY. GOD! Zelda YOU ARE MOST DEFINITELY LIKE THE BEST MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY SOMEWHERE"

(defn no-params
  []
  "I take no parameters!")
(no-params)
;; "I take no parameters!"

(defn one-param
  [x]
  (str "I take one parameter: " x))

(defn two-params
  [x y]
  (str "Two parameters! That's nothing! Pah! I will smoosh them "
  "together to spite you! " x y))

(defn multi-arity
  ([first-arg second-arg third-arg]
   (+ first-arg second-arg third-arg))
  ([first-arg second-arg]
   (* first-arg second-arg))
  ([first-arg]
   (str first-arg)))
(multi-arity 1 2 3)
;; 6
(multi-arity 4 5)
;; 20
(multi-arity "hello")
;; "hello"

;; Arity overloading is one way to provide default values for arguments.
(defn x-chop
  "Describe the kind of chop you're inflicting on someone"
  ([name chop-type]
   (str "I " chop-type " chop " name "! Take that!"))
  ([name]
   (x-chop name "karate")))

(x-chop "Kanye West" "slap")
;; "I slap chop Kanye West! Take that!"

(x-chop "Kanye East")
;; "I karate chop Kanye East! Take that!"

;; Clojure also allows you to define variable-arity functions by including a rest parameter,
;; as in “put the rest of these arguments in a list with the following name.”
;; The rest parameter is indicated by an ampersand (&)

(defn codger-communication
  [whippersnapper]
  (str "Get off my lawn" whippersnapper "!!!"))

(defn codger
  [& whippersnappers]
  (map codger-communication whippersnappers))

(codger "Billy" "Anne-Marie" "The Incredible Bulk")
;; => ("Get off my lawnBilly!!!" "Get off my lawnAnne-Marie!!!" "Get off my lawnThe Incredible Bulk!!!")

;; You can mix rest parameters with normal parameters, but the rest parameter has to come last:
(defn favorite-things
  [name & things]
  (str "Hi, " name ", here are my favorite things: "
       (clojure.string/join "," things)))

(favorite-things "Doreen" "gum" "shoes" "kara-te")
;; => "Hi, Doreen, here are my favorite things: gum,shoes,kara-te"

;; Destructuring
(defn my-first
  [[first-thing]]
  first-thing)

(my-first ["oven" "bike" "war-axe"])
;; => "oven"

(defn chooser
  [[first-choice second-choice & unimportant-choices]]
  (println (str "Your first choice is: " first-choice))
  (println (str "Your second choice is: " second-choice))
  (println (str "We're ignoring the rest of your choices. "
                "Here they are in case you need to cry over them: "
                (clojure.string/join ", " unimportant-choices))))

(chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])

;; Destructuring map
(defn announce-treasure-location
  [{lat :lat lng :lng}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

(announce-treasure-location {:lat 28.22 :lng 81.33})

;; We often want to just break keywords out of a map, so there’s a shorter syntax for that.
;; This has the same result as the previous example:
(defn announce-treasure-location2
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng)))

;; You can retain access to the original map argument by using the :as keyword.
;; In the following example, the original map is accessed with treasure-location:

(defn receive-treasure-location
  [{:keys [lat lng] :as treasure-location}]
  (println (str "Treasure lat: " lat))
  (println (str "Treasure lng: " lng))

  ;; One would assume that this would put in new coordinates for your ship
  (:lat treasure-location))

;; Function Body
;; The function body can contain forms of any kind. Clojure automatically returns the last form evaluated.
(defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")
(illustrative-function)
;; => joe

(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh! What a big number!"
    "That number's OK, I guess"))
(number-comment 5)
;; "That number's OK, I guess"
(number-comment 7)
;; "Oh my gosh! What a big number!"



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

