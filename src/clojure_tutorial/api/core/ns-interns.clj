;; ns-interns
;; (ns-interns ns)

;; Returns a map of the intern mappings for the namespace.

(ns-interns *ns*)
;; => {}

(def x {:a 1})
(ns-interns *ns*)
;; => {x #'user/x}

(get (ns-interns *ns*) 'x)
;; => #'user/x

(take 2 (ns-interns `clojure.core))
;; => ([primitives-classnames #'clojure.core/primitives-classnames] [+' #'clojure.core/+'])

(count (ns-interns `clojure.core))
;; => 699
