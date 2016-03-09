;; merge-with
;; (merge-with f & maps)
;; Returns a map that consists of the rest of the maps conj-ed onto
;; the first.  If a key occurs in more than one map, the mapping(s)
;; from the latter (left-to-right) will be combined with the mapping in
;; the result by calling (f val-in-result val-in-latter).

(merge-with concat
	  {"Lisp" ["Common Lisp" "Clojure"]
	   "ML" ["Caml" "Objective Caml"]}
	  {"Lisp" ["Scheme"]
	   "ML" ["Standard ML"]})
;; => {"Lisp" ("Common Lisp" "Clojure" "Scheme"), "ML" ("Caml" "Objective Caml" "Standard ML")}

(merge-with +
            {:a 1  :b 2}
            {:a 9  :b 98 :c 0})
;; => {:c 0, :a 10, :b 100}

;; 'merge-with' works with an arbitrary number of maps:
(merge-with +
           {:a 1  :b 2}
           {:a 9  :b 98  :c 0}
           {:a 10 :b 100 :c 10}
           {:a 5}
           {:c 5  :d 42})
;; => {:d 42, :c 15, :a 25, :b 200}
