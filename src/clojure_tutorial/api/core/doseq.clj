(ns clojure-tutorial.api.core.doseq)

;doseq
;(doseq seq-exprs & body)
;Repeatedly executes body (presumably for side-effects) with bindings and filtering as provided by "for".
; Does not retain the head of the sequence. Returns nil.

(defn run-report [user]
  (println "Running report for" user))

(defn dispatch-reporting-job [all-users]
  (doseq [user all-users]
    (run-report user)))

(dispatch-reporting-job ["a" "b" "c"])
;Running report for a
;Running report for b
;Running report for c
;nil

(doseq [x [1 2 3]]
  (prn (* x x)))
;1
;4
;9
;nil

(doseq [x [1 2 3]
        y [1 2 3]]
  (prn (* x y)))
;1
;2
;3
;2
;4
;6
;3
;6
;9
;nil