(ns
  ^{:author edgar}
  clojure_tutorial.api.core.restart-agent)

;restart-agent
;(restart-agent a new-state & options)

;When an agent is failed, changes the agent state to new-state and then un-fails the agent so that sends are allowed again.
; If a :clear-actions true option is given, any actions queued on the agent that were being held while it was failed will be discarded, otherwise those held actions will proceed.
; The new-state must pass the validator if any, or restart will throw an exception and the agent will remain failed with its old state and error.
; Watchers, if any, will NOT be notified of the new state.
; Throws an exception if the agent is not failed.

(def a (agent nil))
(send a (fn [_] (throw (Exception. "something is wrong"))))
;;#<Agent@747f281: nil>
a
;;#<Agent@747f281 FAILED: nil>
(send a identity)
;;Exception something is wrong

;尝试给一个已经挂掉的agent发送action会返回导致这个agent挂掉的异常。
; 如果想显示获取这个异常，可以使用agent-error函数，它会返回这个异常或者nil——如果这个agent并没有挂掉的话

(agent-error a)
;;#<Exception java.lang.Exception: something is wrong>

;可以通过restart-agent来重启一个挂掉的agent，它会将agent的状态重置成我们提供的值，并且使它能够继续接收action，
; 它还可以接受一个可选的标志参数：clear-actions，如果指定了这个参数的话，它会把这个agent挂掉的时候它上面阻塞着的所有action清除掉，
; 否则，这些阻塞的action在agent重启之后会立即执行，从而可能使得agent重启之后马上又挂掉

(restart-agent a 42)
;;42
(send a inc)
;;#<Agent@747f281: 42>
(reduce send a (for [x (range 3)]
                 (fn [_] (throw (Exception. (str "error #" x))))))
;;#<Agent@747f281: 43>
(agent-error a)
;;#<Exception java.lang.Exception: error #0>
(restart-agent a 42)
;;42
(agent-error a)
;;Exception java.lang.Exception: error #1>
(restart-agent a 42 :clear-actions true)
;;42
(agent-error a)
;;nil