;; let
;; (let [bindings*] exprs*)

;; binding => binding-form init-expr
;;  Evaluates the exprs in a lexical context in which the symbols in
;; the binding-forms are bound to their respective init-exprs or parts
;; therein.

(let [x 3]
  x)
;; => 3

(def dalmatian-list
  ["Pongo" "Perdita" "Puppy 1" "Puppy 2"])

(let [dalmatians (take 2 dalmatian-list)]
  dalmatians)
;; => ("Pongo" "Perdita")

;; let also introduces a new scope:
(def x 0)
(let [x 1] x)
;; => 1

;; You can also use rest parameters in let
(let [[pongo & dalmatians] dalmatian-list]
  [pongo dalmatians])
;; => ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]
