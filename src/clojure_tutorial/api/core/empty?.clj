;; empty?
;; (empty? coll)

;; Returns true if coll has no items - same as (not (seq coll)).
;; Please use the idiom (seq x) rather than (not (empty? x))

(empty? '())
;; => true

(empty? '(1))
;; => false
