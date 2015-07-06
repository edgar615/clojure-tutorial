(ns
  ^{:author edgar}
  clojure_tutorial.api.core.await)

;await
;(await & agents)

;Blocks the current thread (indefinitely!) until all actions dispatched thus far,
; from this thread or agent, to the agent(s) have occurred.
; Will block on failed agents.  Will never return if a failed agent is restarted with :clear-actions true.

(def a (agent 5000))
(def b (agent 10000))
(send-off a #(Thread/sleep %))

(send-off b #(Thread/sleep %))

@a

;await可以等等所有从这个线程上发送到agent的action都求值c。
(await a b)

@a