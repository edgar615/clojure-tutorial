;; use
;; (use & args)

;; Like 'require, but also refers to each lib's namespace using
;; clojure.core/refer. Use :use in the ns macro in preference to calling
;; this directly.
;;  'use accepts additional options in libspecs: :exclude, :only, :rename.
;; The arguments and semantics for :exclude, :only, and :rename are the same
;; as those documented for clojure.core/refer.

;; Imports only the split function from clojure.string.
(use '[clojure.string :only (split)])

;; split is now available without a namespace qualification.
(split "hello world" #" ")
;; => ["hello" "world"]

;; You can also add the :as keyword to import the rest of clojure.string
;; with a namespace qualification.
(use '[clojure.string :as s :only (split)])
;; Now we can access any function in clojure.string using s.
(s/replace "foobar" "foo" "squirrel")
;; => "squirrelbar"

;; And we can still call split with or without the s qualification.
(split "hello world" #" ")
;; => ["hello" "world"]
(s/split "hello world" #" ")
;; => ["hello" "world"]
