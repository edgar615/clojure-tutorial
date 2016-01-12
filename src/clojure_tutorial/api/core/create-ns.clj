;; create-ns
;; (create-ns sym)

;; Create a new namespace named by the symbol if one doesn't already
;; exist, returns it or the already-existing namespace of the same
;; name.

(ns-name *ns*)
(create-ns 'my-new-namespace)
;; => #<Namespace my-new-namespace>
(ns-name *ns*)
(ns-name (create-ns 'cheese.taxonomy))
;; => cheese.taxonomy
