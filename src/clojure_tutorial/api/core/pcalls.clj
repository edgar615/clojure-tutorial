(ns clojure-tutorial.api.core.pcalls)
;pcalls
;(pcalls & fns)
;Executes the no-arg fns in parallel, returning a lazy sequence of their values

;接受任意数量无参函数作为参数，返回一个包含它们返回值的惰性序列