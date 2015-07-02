(ns clojure-tutorial.api.core.alter)

;alter
;(alter ref fun & args)
;Must be called in a transaction. Sets the in-transaction-value of ref to:
;(apply fun in-transaction-value-of-ref args)
;and returns the in-transaction-value of ref.

;alter函数
;参数：要被修改的ref、一个函数f以及这个函数所需要的其他参数。当alter函数返回的时候，ref在这个“事务内的值”会被改变成函数f的返回结果

;所有对于ref的状态进行修改的函数是在一个独立的时间线上执行的，这个时间线开始的时间是这个ref第一次被修改的时候。
;接下来对于ref的所有修改、访问都是在这个独立的时间线上进行的，而这个时间线只在这个事务内存在，而且只能在这个事务中被访问。
;当控制流要离开这个事务的时候，STM会尝试提交这个事务，在最乐观的情况下，提交会成功，ref的状态被修改成这个事务内ref的新值
;而这个新值会对所有的线程/事务可见——不只是在某个事务内可见了。但是如果外部的时间线已经对ref的状态进行了修改，并且已经提交了，那么事务提交就会跟它发生冲突，
;这会导致整个事务重试，利用ref的新值来重新执行一遍。

;在这个过程中，任何只读线程（比如解引用）不会被阻塞住或者需要等待。而且那些对ref的值进行修改的线程直到成功提交之后，它们对于ref的修改才会对其他线程可见，
;也就不会影响其他线程对于ref的只读操作了。

;alter的独特语义是，当一个事务要提交的时候，ref的全局值必须跟这个事务内第一次调用alter时候的值一样，否则整个事务会被重启，从头再执行一遍。

;Clojure的STM可以这样理解：它是一个乐观地尝试对并发的修改操作进行重新排序，以使得它们可以顺序地执行的一个过程。

(def names (ref []))

(defn add-name
  [name]
  (dosync
    (alter names conj name)))

(add-name "zack")
;;["zack"]

(add-name "shelley")
;;["zack" "shelley"]

@names
;;["zack" "shelley"]