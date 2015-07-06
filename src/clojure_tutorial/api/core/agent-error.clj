(ns
  ^{:author edgar}
  clojure_tutorial.api.core.agent-error)

;agent-error
;(agent-error a)

;Returns the exception thrown during an asynchronous action of the agent if the agent is failed.
; Returns nil if the agent is not failed.

(def tdate (agent (java.util.Date.)))

@tdate
;;#inst "2015-07-06T07:43:22.796-00:00"

(send tdate inc)
;;#<Agent@1e13529a FAILED: #inst "2015-07-06T07:43:22.796-00:00">

(agent-error tdate)
;;#<ClassCastException java.lang.ClassCastException: java.util.Date cannot be cast to java.lang.Number>