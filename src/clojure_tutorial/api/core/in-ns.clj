;; in-ns
;; (in-ns name)
;; Sets *ns* to the namespace named by the symbol, creating it if needed.

(ns-name *ns*)
;; => user

(in-ns 'cheese.analysis)
;; => #<Namespace cheese.analysis>

(ns-name *ns*)
;; => cheese.analysis

(in-ns 'cheese.taxonomy)
;; => #<Namespace cheese.taxonomy>

(def cheddars ["mild" "medium" "strong" "sharp" "extra sharp"])
;; => #'cheese.taxonomy/cheddars

(in-ns 'cheese.analysis)
;; => #<Namespace cheese.analysis>
;; cheddars
;; => clojure.lang.Compiler$CompilerException: java.lang.RuntimeException: Unable to resolve symbol

cheese.taxonomy/cheddars
;; => ["mild" "medium" "strong" "sharp" "extra sharp"]
