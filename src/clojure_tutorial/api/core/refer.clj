;; refer
;; (refer ns-sym & filters)

;; refers to all public vars of ns, subject to filters.
;; filters can include at most one each of:
;; :exclude list-of-symbols
;; :only list-of-symbols
;; :rename map-of-fromsymbol-tosymbol
;;  For each public interned var in the namespace named by the symbol,
;; adds a mapping from the name of the var to the var to the current
;; namespace.  Throws an exception if name is already mapped to
;; something else in the current namespace. Filters can be used to
;; select a subset, via inclusion or exclusion, or to provide a mapping
;; to a symbol different from the var's name, in order to prevent
;; clashes. Use :use in the ns macro in preference to calling this directly.

(in-ns 'cheese.taxonomy)
;; => #<Namespace cheese.taxonomy>
(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
;; => #'cheese.taxonomy/cheddars
(def bries ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"])
;; => 'cheese.taxonomy/bries

(in-ns 'cheese.analysis)
;; => #<Namespace cheese.analysis>
(refer 'cheese.taxonomy)
bries
;; => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]

(in-ns 'cheese.another)
bries
;; => clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Unable to resolve symbol: bries

(in-ns 'cheese.analysis)
(clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'bries)
;; => #'cheese.taxonomy/bries
(clojure.core/get (clojure.core/ns-map clojure.core/*ns*) 'cheddars)
;; => #'cheese.taxonomy/cheddars

;; When you call refer, you can also pass it the filters :only, :exclude, and :rename.
;; As the names imply, :only and :exclude restrict which symbol/var mappings get merged into the current namespace’s ns-map.
;; :rename lets you use different symbols for the vars being merged in.
;; Here’s what would happen if we had modified the preceding example to use :only:
(in-ns 'cheese.another)
(refer 'cheese.taxonomy :only ['bries])
bries
;; => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
cheddars
;; => clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Unable to resolve symbol

;; And here’s :exclude in action:
(in-ns 'cheese.another2)
(refer 'cheese.taxonomy :exclude ['bries])
bries
;; => clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Unable to resolve symbol
cheddars
;; => ["mild" "medium" "strong" "sharp" "extra sharp"]

;; Lastly, a :rename example:
(in-ns 'cheese.another3)
(refer 'cheese.taxonomy :rename {'bries 'yummy-bries})
bries
;; => clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Unable to resolve symbol
yummy-bries
;; => ["Wisconsin" "Somerset" "Brie de Meaux" "Brie de Melun"]
