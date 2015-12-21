;reduce
;    (reduce f coll)
;(reduce f val coll)

;f should be a function of 2 arguments. If val is not supplied,returns the result of applying f to the first 2 items in coll,
; then applying f to that result and the 3rd item, etc. If coll contains no items, f must accept no arguments as well,
; and reduce returns the result of calling f with no arguments.  If coll has only 1 item, it is returned and f is not called.
; If val is supplied, returns the result of applying f to val and the first item in coll,
; then applying f to that result and the 2nd item, etc. If coll contains no items, returns val and f is not called.

(reduce + [1 2 3 4 5])                                      ;;15
(reduce + [])                                               ;;0
(reduce + 1 [])                                             ;;1
(reduce + 1 [2 3])                                          ;;6

(reduce
  (fn [m v]
    (assoc m v (* v v)))
  {}
  [1 2 3 4])
;; => {4 16, 3 9, 2 4, 1 1}

(reduce
 (fn [new-map [key val]]
   (assoc new-map key (inc val)))
 {}
 {:max 30 :min 10})
;; => {:min 11, :max 31}

(reduce
 (fn [new-map [key val]]
   (if (> val 4)
     (assoc new-map key val)
     new-map))
 {}
 {:human 4.1
         :critter 3.9})
;; => {:human 4.1}
