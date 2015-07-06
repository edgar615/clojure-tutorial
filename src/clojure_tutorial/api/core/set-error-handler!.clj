(ns
  ^{:author edgar}
  clojure_tutorial.api.core.set-error-handler!)

;set-error-handler!
;(set-error-handler! a handler-fn)

;Sets the error-handler of agent a to handler-fn.
; If an action being run by the agent throws an exception or doesn't pass the validator fn,
; handler-fn will be called with two arguments: the agent and the exception.

(def a (agent nil
         :error-mode :continue
         :error-handler (fn [the-agent exception]
                          (.println System/out (.getMessage exception)))))

(send a (fn [_] (throw (Exception. "something is wrong"))))
;;#<Agent@24c4ddae: nil>
;;something is wrong
(send a identity)
;;#<Agent@24c4ddae: nil>

;在错误处理器中关闭agent
(set-error-handler! a (fn [the-agent exception]
                        (when (= "FATAL" (.getMessage exception))
                          (set-error-mode! the-agent :fail))))

(send a (fn [_] (throw (Exception. "FATAL"))))
;;#<Agent@24c4ddae FAILED: nil>
(send a identity)
;;Exception FATAL

;;I/O、事务以及嵌套的sent

;一个事务内通过send和send-off发送的action会被保持到事务成功提交。
;这意味着如果一个事务被重试100次，被发送给agent的action也在事务成功提交之后被执行一次。
;类似的，在一个agent action内部，对send和send-off的调用称为“嵌套发送”——也会被保持到直到这个action完成。
;在这两种情况下，被发送的action都可能因为一个校验器的失败而被全部抛弃掉。

;(require '[clojure.java.io :as io])
;(def console (agent *out*))
;(def character-log (agent (io/writer "character-states.log" :append true)))