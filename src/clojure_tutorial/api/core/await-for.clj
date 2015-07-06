(ns
  ^{:author edgar}
  clojure_tutorial.api.core.await-for)

;await-for
;(await-for timeout-ms & agents)
;Blocks the current thread until all actions dispatched thus far (from this thread or agent) to the agents have occurred,
; or the timeout (in milliseconds) has elapsed.
; Returns logical false if returning due to timeout, logical true otherwise.
