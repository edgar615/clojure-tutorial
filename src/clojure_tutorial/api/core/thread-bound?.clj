(ns
  ^{:author edgar}
  clojure_tutorial.api.core.thread-bound?)

;thread-bound?
;(thread-bound? & vars)

;Returns true if all of the vars provided as arguments have thread-local bindings.
;Implies that set!'ing the provided vars will succeed.  Returns true if no vars are provided.

(thread-bound? #'map)
;;false
(thread-bound? #'*warn-on-reflection*)
;;true