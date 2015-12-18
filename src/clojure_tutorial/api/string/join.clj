;; join

;; (join coll)
;; (join separator coll)

;; Returns a string of all elements in coll, as returned by (seq coll),
;;  separated by an optional separator.

(clojure.string/join "," [1 2 3])
;; => 1,2,3

(clojure.string/join [1 2 3])
;; => 123


(use '[clojure.string :only (join split)])

(println
         (join "\n"
           (split "The Quick Brown Fox" #"\s")))

;; The
;; Quick
;; Brown
;; Fox
;; nil
