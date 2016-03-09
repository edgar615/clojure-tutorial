;原子类型 Atom
;原子类型是Clojure最基本的引用类型；它实现的是同步的、无须协调的、原子的”先比较再设值“的修改策略
;因此，对原子类型的修改操作都要阻塞直到这个修改完成，每一个修改操作都是完全隔离的——自动隔离，没有办法协调对两个atom的修改


;swap!是最常见的对于原子类型的修改操作，它接受要修改的原子类型，一个函数以及一些额外的参数作为参数，它把当前这个原子类型的状态修改成对传入函数的返回值

(def sarah (atom {:name "Sarah" :age 25 :wears-glasses? false}))

(swap! sarah update-in [:age] + 3)
;;{:age 28, :name "Sarah", :wears-glasses? false}

;每一个对原子类型的修改都会原子地发生，所以我们可以非常安全地对原子类型的值调用任何函数，或者多个函数同时来修它进行修改。
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

;compare-and-set!
;如果你已经知道要修改的原子类型的值是什么的话，可以使用这个函数，如果修改成功的话，这个函数会返回true
(compare-and-set! xs :wrong "new value")
;;false
(compare-and-set! xs @xs "new value")
;;true
@xs
;;"new value"

;compare-and-set!不能使用值语义，它要求atom的值跟你传给它的第二个参数必须一样
(def xs (atom #{1 2}))
(compare-and-set! xs #{1 2} "new value")
;;false


;通知和约束
;引用类型还有两个共同的特性：对引用的状态进行监控，对要赋给引用的新的状态的合法性近线校验

;观察器
;观察器是在引用的状态发生改变的时候会被调用的函数。
;所有的引用类型一开始都是没有观察器的，可以在任何时间给它注册一个观察器或者移除一个观察器。
;一个观察器函数毕夏接受４个参数：key，发生改变的引用，引用的旧状态以及现在的新状态

(defn echo-watch
  [key identity old new]
  (println key old "=>" new))
(def sarah (atom {:name "Sarah" :age 25}))
(add-watch sarah :echo echo-watch)
;;#<Atom@3942d14f: {:age 25, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 25, :name Sarah} => {:age 26, :name Sarah}
;;{:age 26, :name "Sarah"}
(add-watch sarah :echo2 echo-watch)
;;#<Atom@3942d14f: {:age 26, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 26, :name Sarah} => {:age 27, :name Sarah}
;;:echo2 {:age 26, :name Sarah} => {:age 27, :name Sarah}
;;{:age 27, :name "Sarah"}

;删除一个观察者
(remove-watch sarah :echo2)
;;#<Atom@3942d14f: {:age 27, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 27, :name Sarah} => {:age 28, :name Sarah}
;;{:age 28, :name "Sarah"}


;这些观察者函数在引用类型每次被修改的时候都会被调用，但是它不保证调用的时候引用的状态确实发生了变化
(reset! sarah @sarah)
;;:echo {:age 28, :name Sarah} => {:age 28, :name Sarah}
;;{:age 28, :name "Sarah"}

;观察器函数对于使得本地改变可以即时通知到其他引用活摘其他系统来说非常方便

;实现一个记录引用的所有历史状态的功能
(def history (atom ()))

(defn log->list
  [dest-atom key source old new]
  (when (not= old new)
    (swap! dest-atom conj new)))

(def sarah (atom {:name "Sarah" :age 25}))
(add-watch sarah :record (partial log->list history))
;;#<Atom@7478d2c4: {:age 25, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;{:age 26, :name "Sarah"}
(swap! sarah update-in [:age] inc)
;;{:age 27, :name "Sarah"}
(swap! sarah identity)
;;{:age 27, :name "Sarah"}
(swap! sarah assoc :wears-glasses? true)
;;{:age 27, :name "Sarah", :wears-glasses? true}
(swap! sarah update-in [:age] inc)
;;{:age 28, :name "Sarah", :wears-glasses? true}
(pprint @history)
;;({:age 28, :name "Sarah", :wears-glasses? true}
;;  {:age 27, :name "Sarah", :wears-glasses? true}
;;  {:age 27, :name "Sarah"}
;;  {:age 26, :name "Sarah"})

;校验器
;校验器使你可以以任何想要的方式对引用的状态进行控制。
;校验器函数是一个只接受一个参数的函数，它在新状态被设置到引用之前被调用。如果校验器返回逻辑false或者抛出一个异常的话，那么这个状态修改会失败并抛出一个异常
(def n (atom 1 :validator pos?))
(swap! n + 500)                                             ;;501
(swap! n - 1000)                                            ;;IllegalStateException Invalid reference state
;因为校验器值接受一个参数，因此可以使用Clojure很多已经存在的谓词来作为校验器

;虽然所有的引用类型都可以关联一个校验器，但是只有原子类型、ref、agent可以在创建的时候通过:validator关键字函数直接指定一个校验器。
;如果要给var添加一个校验器，或者要改变atom、ref或agent的校验器，可以使用set-validator!函数

(def sarah (atom {:name "Sarah" :age 25}))
(set-validator! sarah :age)
(swap! sarah dissoc :age)                                   ;;IllegalStateException Invalid reference state
;可以抛出特定的异常来使我们知道为什么修改状态会失败，而不是简单地返回false
(set-validator! sarah #(or (:age %)
                           (throw (IllegalStateException. "People must have ':age'"))))
(swap! sarah dissoc :age)                                   ;;IllegalStateException People must have ':age'
