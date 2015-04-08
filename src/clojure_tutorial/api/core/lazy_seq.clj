(ns clojure-tutorial.api.core.lazy-seq)

;lazy-seq
;(lazy-seq & body)

;Takes a body of expressions that returns an ISeq or nil, and yields a Seqable object that will invoke the body only the first time seq is called,
; and will cache the result and return it on all subsequent seq calls. See also - realized?

(defn random-ints
  [limit]
  (lazy-seq
    (println "realizing random number")
    (cons (rand-int limit)
          (random-ints limit))))
(def rands (take 10 (random-ints 50)))

(first rands)
;realizing random number
;16
(nth rands 3)
;realizing random number
;realizing random number
;realizing random number
;4
(count rands)
;realizing random number
;realizing random number
;realizing random number
;realizing random number
;realizing random number
;realizing random number
;10
(count rands)
;10
