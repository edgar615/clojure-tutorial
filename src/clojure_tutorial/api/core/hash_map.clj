(ns clojure-tutorial.api.core.hash-map)

; (hash-map) (hash-map & keyvals)
;keyval => key val
;Returns a new hash map with supplied mappings.  If any keys are equal, they are handled as if by repeated uses of assoc.

(hash-map)                                                  ;{}
(hash-map :a 1 :a 2)                                        ;{:a 2}
{}                                                          ;{}
{:a 1 :a 2}                                                 ;IllegalArgumentException Duplicate key: :a