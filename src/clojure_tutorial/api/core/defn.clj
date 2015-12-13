;; defn
;; (defn name doc-string? attr-map? [params*] prepost-map? body)
;; (defn name doc-string? attr-map? ([params*] prepost-map? body) + attr-map?)

;; Same as (def name (fn [params* ] exprs*)) or (def
;;   name (fn ([params* ] exprs*)+)) with any doc-string or attrs added
;;   to the var metadata. prepost-map defines a map with optional keys
;;   :pre and :post that contain collections of pre or post conditions.

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
