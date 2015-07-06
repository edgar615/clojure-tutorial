(ns
  ^{:author edgar}
  clojure_tutorial.concurrent.agent)

;agent是一种不进行协调的、异步的引用类型，这意味着对于一个agent的状态的修改与对别的agent的状态的修改是完全独立的，
;而且发起对agent进行改变的线程跟真正改变agent值的线程不是同一个线程。
; 可以很安全地利用agent进行I/O以及其他副作用操作
; agent是STM感知的，因此它们可以很安全地用在事务重试的场景下

;agent的状态可以通过两个函数：send和send-off进行更新
;参数：一个更新函数，这个更新函数以要更新的agent的当前状态以及额外的一些参数为参数，返回的结果则是这个agent的新状态

;这些元素加在一起:更新函数加上发送给send或者send-off额外的参数叫做一个agent action，
;而每个agent维护一个action的队列。对于send和send-off的调用都会立马返回，它们只是简单地把这个action放到
;agent的action队列上去，然后这些action会以它们被发送过去的顺序在一些专门用来执行这些agent action的线程上串行执行。
;而每个action的结果都是agent的一个新状态

;使用send发送的action是在一个固定大小的线程池中执行，线程池的大小不会超过当前硬件的并行能力。
;因此send不适宜用来发送那种进行I/O操作的action（或者其他类型的阻塞操作），
;因为它们会阻止其他CPU密集型action更好地利用计算机的计算资源

;由send-off发送的action则会在一个不限制大小的线程池（跟future利用的是同一个线程池）中执行，
; 因此适宜用来发送那些阻塞的、非CPU密集型的action

(def a (agent 500))

(send a range 1000)
;;#<Agent@503f91c3: (500....
@a
;;(500 501 502 503 504 505 ....)

;执行很快的action
(def a (agent 0))

(send a inc)


(def a (agent 5000))
(def b (agent 10000))
(send-off a #(Thread/sleep %))

(send-off b #(Thread/sleep %))

@a

;await可以等等所有从这个线程上发送到agent的action都求值完。
(await a b)

@a

;处理agent action中的错误
;因为agent action是异步执行的，从action里面抛出一个异常跟发送这个action的不是一个线程。
;对这种情况的默认处理策略是这个agent默默地挂掉:你还是可以对它进行解引用以获取它最后的状态，但是进一步给它发送action会失败

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


;agent的错误处理器以及模式
;默认的错误处理行为：一个错误会导致agent进入失败状态是agent支持的两种失败模式之一。
; agent接受一个:error-mod参数，它有两个可选值： :fail（默认值）以及:continue。
;如果一个agent的错误处理状态被设置成:continue，那么当action在执行的时候会抛出异常，
; 这个异常会被直接忽略掉继续执行对了中其他action，同时也可以继续接收新的action

(def a (agent nil :error-mode :continue))

(send a (fn [_] (throw (Exception. "something is wrong"))))
;;#<Agent@e3c0e40: nil>
(send a identity)
;;#<Agent@e3c0e40: nil>

;错误处理器
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