;; alias

;; (alias alias namespace-sym)

;; Add an alias in the current namespace to another
;; namespace. Arguments are two symbols: the alias to be used, and
;; the symbolic name of the target namespace. Use :as in the ns macro in preference
;; to calling this directly.

(alias 'string 'clojure.string)

(string/capitalize "hONdURas")
;; => "Honduras"

;; The alias can also be created when the
;; namespace is required using the :as keyword.

(require '[clojure.string :as str])
(str/capitalize "hONduRas")
;; => "Honduras"
