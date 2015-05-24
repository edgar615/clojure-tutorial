(ns clojure-tutorial.api.core.swap!)

;swap!
;    (swap! atom f)(swap! atom f x)(swap! atom f x y)(swap! atom f x y & args)
;Atomically swaps the value of atom to be:(apply f current-value-of-atom args).
; Note that f may be called multiple times, and thus should be free of side effects.
; Returns the value that was swapped in.

;swap!是最常见的对于原子类型的修改操作，它接受要修改的原子类型，一个函数以及一些额外的参数作为参数，它把当前这个原子类型的状态修改成对传入函数的返回值

(def sarah (atom {:name "Sarah" :age 25 :wears-glasses? false}))

(swap! sarah update-in [:age] + 3)
;;{:age 28, :name "Sarah", :wears-glasses? false}

;每一个对原子类型的修改都会原子地发生，所以我们可以非常安全地对原子类型的值调用任何函数，活摘多个函数同时来修它进行修改。
;没有其他线程会看到这个原子类型在值被更改过程中的某个中间状态

(swap! sarah (comp #(update-in % [:age] inc)
                   #(assoc % :wears-glasses? true)))
;;{:age 29, :name "Sarah", :wears-glasses? true}

;swap!的语义是先比较旧的值是否匹配，任何再设新值，所以如果原子类型的值在你的更新函数返回之前发生了变化，那么swap!会自动进行重试，再次以你
; 的原子类型的新值来调用我们传入的函数。swap!会一直重试，直到设值成功

(def xs (atom #{1 2 3}))
(wait-futures 1 (swap! xs (fn [v]
                            (Thread/sleep 250)
                            (println "trying 4")
                            (conj v 4)))
              (swap! xs (fn [v]
                          (Thread/sleep 500)
                          (println "trying 5")
                          (conj v 5))))

;;trying 4
;;trying 5
;;trying 5
;;nil

@xs
;;#{1 4 3 2 5}

;我们没有办法对swap!的重试语义进行任何控制，因此你提供给swap!的函数必须是纯函数，否则程序执行的结果将很难预料

;作为一个同步的引用类型，修改atom值的函数会阻塞直到修改完成：
(def x (atom 2000))
(swap! x #(Thread/sleep %))
;;上述表达式至少需要两秒才会返回