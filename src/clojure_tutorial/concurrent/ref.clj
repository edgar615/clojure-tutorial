(ns clojure-tutorial.concurrent.ref)

;ref是Clojure提供的协调引用类型。使用它可以保证多个线程可以交互地对这个ref进行操作
; ref会一直保持在一个一致的状态，任何条件下都不会出现外界可见的一致性的状态
; 在多个ref之间不可能会产生竞争条件
; 不需要你手动地去使用锁、monitor或者其他底层的同步原语
; 不可能出现死锁

; 软件事务内存STM是任何对并发修改一系列内存地址的行为进行协调的方法。

; 对于一系列ref的每个修改都是具有事务语义的，而每个STM事务保证对于ref的修改是： 原子的、一致的、隔离的

; Clojure的STM满足ACID属性里面的A、C、I，而D（持久性）则不是STM所关心的，因为STM是纯内存的实现


;创建游戏角色的函数
(defn character
  [name & {:as opts}]
  (ref (merge {:name name :items #{} :health 500}
              opts)))

;定义一些具体的角色
(def smaug (character "Smaug" :health 500 :strength 400 :items (set (range 50))))
(def bilbo (character "Bilbo" :health 100 :strength 100))
(def gandalf (character "Gandalf" :health 75 :mana 750))

;dosync规定了一个事务的边界，所有对于ref的修改毕夏要发生在一个事务里面，对于这些修改的处理是以同步的方式进行的。
;也就是说，启动一个事务的线程在这个事务完成之前是不能继续执行其他代码的

;两个对共享ref进行修改的事务冲突与否，是由对ref进行修改所使用的函数来决定的。
; 有三个函数：alter、commute以及ref-set——它们每一个都有不同的、是否产生（或者避免）冲突的语义

;把一个角色的武器转移到另外一个角色那里的函数
(defn loot
  [from to]
  (dosync
    (when-let [items (first (:items @from))]
      (alter to update-in [:items] conj items)
      (alter from update-in [:items] disj items))))

;接受表达式作为参数，返回n个future
(defmacro futures
  [n & exprs]
  (vec (for [_ (range n)
             expr exprs]
         `(future ~expr))))
;wait-futures，始终返回nil并且阻塞REPL直到所有的future都实例化
(defmacro wait-futures
  [& args]
  `(doseq [f# (futures ~@args)]
     @f#))

;Bilbo和Gandalf瓜分Smaug的武器
(wait-futures 1
              (while (loot smaug bilbo))
              (while (loot smaug gandalf)))
;测试瓜分武器的正确性
(map (comp :items deref) [bilbo gandalf])
;;(#{20 27 1 24 15 48 22 36 29 44 6 28 25 34 17 3 12 2 23 47 35 19 11 5 14 45 26 16 38 30 10 18 42 37 49} #{0 7 39 46 4 21 31 32 40 33 13 41 43 9 8})

(filter (:items @bilbo) (:items @gandalf))
;;()

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


;利用commute来最小化事务冲突
;因为alter没有对修改ref的可重排序性做任何假设，因此它对ref状态进行修改的最安全的方式，不过，在一些情况下，可以安全把对ref的修改的操作进行重排序；
;这样就可以使用commute来代替alter，可以减少潜在冲突几率以及事务重试次数，从而最大化总体的吞吐量。

;commute应该只用在可以对修改ref状态的操作进行重排的场景中。
;commute跟alter有两个方面的不同。首先alter的返回值是要更新到这个ref的全局状态的值，这个事务内的值回收最终提交的值。
;而另一方面，由commute产生的事务内的值不一定会是最终提交的那个值，因为所有被commute的函数在最终提交的时候会利用ref的最小全局值重新计算一遍。

;利用commute对ref进行的修改从来不会导致冲突，因此也从来不会导致一个事务重试。这个显然潜在地提高了性能以及吞吐量

(def x (ref 0))

(time (wait-futures 5
                    (dotimes [_ 1000]
                      (dosync (alter x + (apply + (range 1000)))))
                    (dotimes [_ 1000]
                      (dosync (alter x - (apply + (range 1000)))))))
;;"Elapsed time: 3289.451101 msecs"
(time (wait-futures 5
                    (dotimes [_ 1000]
                      (dosync (commute x + (apply + (range 1000)))))
                    (dotimes [_ 1000]
                      (dosync (commute x - (apply + (range 1000)))))))
;;"Elapsed time: 850.658269 msecs"

;;使用commute有问题的函数
(defn flawed-loot
  [from to]
  (dosync
    (when-let [item (first (:items @from))]
      (commute to update-in [:items] conj item)
      (commute from update-in [:items] disj item))))

(def smaug (character "Smaug" :health 500 :strength 400 :items (set (range 50))))
(def bilbo (character "Bilbo" :health 100 :strength 100))
(def gandalf (character "Gandalf" :health 75 :mana 750))

;Bilbo和Gandalf瓜分Smaug的武器
(wait-futures 1
              (while (flawed-loot smaug bilbo))
              (while (flawed-loot smaug gandalf)))
;测试瓜分武器的正确性
(map (comp count :items deref) [bilbo gandalf])
;;(43 49)

(filter (:items @bilbo) (:items @gandalf))
;;(0 7 20 27 1 24 39 4 15 48 21 31 32 40 33 13 36 43 29 6 28 34 17 12 2 23 47 19 11 9 5 14 45 26 16 38 30 10 18 42 8 49)

;修改
(defn fixed-loot
  [from to]
  (dosync
    (when-let [item (first (:items @from))]
      (commute to update-in [:items] conj item)
      (alter from update-in [:items] disj item))))

(def smaug (character "Smaug" :health 500 :strength 400 :items (set (range 50))))
(def bilbo (character "Bilbo" :health 100 :strength 100))
(def gandalf (character "Gandalf" :health 75 :mana 750))

;Bilbo和Gandalf瓜分Smaug的武器
(wait-futures 1
              (while (fixed-loot smaug bilbo))
              (while (fixed-loot smaug gandalf)))
;测试瓜分武器的正确性
(map (comp count :items deref) [bilbo gandalf])
;;(46 4)

(filter (:items @bilbo) (:items @gandalf))
;;()

;;攻击函数
(defn attack
  [aggressor target]
  (dosync
    (let [damage (* (rand 0.1) (:strength @aggressor))]
      (commute target update-in [:health] #(max 0 (- % damage))))))

;;加血函数
(defn heal
  [healer target]
  (dosync
    (let [aid (* (rand 0.1) (:mana @healer))]
      (when (pos? aid)
        (commute healer update-in [:mana] - (max 5 (/ aid 5)))
        (commute target update-in [:health] + aid)))))

;;游戏玩家模拟函数
(def alive? (comp pos? :health))

(defn play
  [character action other]
  (while (and (alive? @character)
              (alive? @other)
              (action character other))
    (Thread/sleep (rand-int 50))))

;bilbo VS smaug
(wait-futures 1
              (play bilbo attack smaug)
              (play smaug attack bilbo))
(map (comp :health deref) [smaug bilbo])
;;(486.0400189366669 0)
;三个人之间的战斗
(dosync
  (alter smaug assoc :health 500)
  (alter bilbo assoc :health 100))

(wait-futures 1
              (play bilbo attack smaug)
              (play smaug attack bilbo)
              (play gandalf heal bilbo))
(map (comp #(select-keys % [:name :health :mana]) deref) [smaug bilbo gandalf])
;;({:health 0, :name "Smaug"} {:health 294.0770800776351, :name "Bilbo"} {:mana -1.871433951409358, :health 75, :name "Gandalf"})

;ref-set来设置ref的状态
;ref-set会把ref的事务内的状态设置到一个给定的值
(dosync [ref-set bilbo {:name "Bilbo"}])

;如果在当前事务提交之前，ref的状态在当前事务之外被改变了的话，这个事务会被重试。
;ref-set通常用来重新初始化ref的状态到初始值


;校验器
;(defn enforce-max-health
;  [{:keys [name health]}]
;  (fn [character-data]
;    (or (<= (:health character-data) health)
;        (throw (IllegalStateException. (str name " is already at max health!"))))))
;(defn character
;  [name & {:as opts}]
;  (let [cdata (merge {:name name :items #{} :health 500}
;                     opts)
;        cdata (assoc cdata :max-health (:health cdata))
;        validators (list* (enforce-max-health name (:health cdata))
;                          (:validators cdata))]
;    (ref (dissoc cdata :validators)
;         :validator #(every? (fn [v] (v %)) validators))))

;只加到最大血量
(defn heal
  [healer target]
  (dosync
    (let [aid (min (* (rand 0.1) (:mana @healer))
                   (- (:max-health @target) (:health @target)))]
      (when (pos? aid)
        (commute healer update-in [:mana] - (max 5 (/ aid 5)))
        (commute target update-in [:health] + aid)))))

(def smaug (character "Smaug" :health 500 :strength 400 :items (set (range 50))))
(def bilbo (character "Bilbo" :health 100 :strength 100 :max-health 130))
(def gandalf (character "Gandalf" :health 75 :mana 750))

(dosync (alter bilbo assoc-in [:health] 95))
;;{:strength 100, :max-health 130, :name "Bilbo", :items #{}, :health 95}
(heal gandalf bilbo)
;;{:strength 100, :max-health 130, :name "Bilbo", :items #{}, :health 130}
(heal gandalf bilbo)
;;nil

;STM的缺点
;事务内绝对不能执行有副作用的函数

;io!宏：当它被放在一个事务中执行，它会抛出一个错误
;因此如果你有一个函数可能会被误用在一个事务里面，那么可以把其中有副作用的部分用io!包起来。
(defn unsafe
  []
  (io! (println "writing to database...")))
(dosync (unsafe))
;;IllegalStateException I/O in transaction

;被ref持有的值一定要是不可变的。Clojure不会阻止你放一个可变对象到ref里面去，但是重试将会导致可变对象最终处于你意料不到的状态
(def x (ref (java.util.ArrayList.)))
(wait-futures 2 (dosync (dotimes [v 5]
                          (Thread/sleep (rand-int 50))
                          (alter x #(doto % (.add v))))))

@x
;;[0 1 0 2 3 4 0 1 2 3 4]


;barging
;在某些情况下，当一个老事务跟一个新事务进行竞争的时候，系统会强迫较新的事务进行重试。而如果barging了一定次数之后这个老事务还是不能成功提交，那么系统会让这个事务失败
(def x (ref 0))
(dosync
  @(future (dosync (ref-set x 0)))
  (ref-set x 1))
;;RuntimeException Transaction failed after reaching retry limit
@x
;;0

;读线程也可能重试
;STM维护事务中涉及的ref的一定长度的历史版本值，这个历史版本的长度在每次事务重试的时候递增。
;这使得在某个时间点，我们的事务终于不用再重试了，因为虽然ref还在被其他事务并行地更新，但是我们需要的值还是在历史记录中

;历史版本的长度可以利用ref-history-cout、ref-max-history以及ref-min-history来查询（以及调整）
;我们创建ref的时候也可以通过指定:min-history和:max-history关键字参数来设置ref历史的最小长度和最大长度
(ref-max-history (ref "abc" :min-history 3 :max-history 30))
;;30

;deref的重试通常出现在那些只读事务中，在这些事务中，我们想获取对一些被活跃修改的ref的快照。可以通过一个单个ref以及一个很慢的只读事务来展示这种情况
(def a (ref 0))
(future (dotimes [_ 500] (dosync (Thread/sleep 200) (alter a inc))))

@(future (dosync (Thread/sleep 1000) @a))
;;57
(ref-history-count a)
;;5
;a通过一些历史记录使得读取事务可以读到它想要读的值。

;加快写事务
(def a (ref 0))
(future (dotimes [_ 500] (dosync (Thread/sleep 20) (alter a inc))))

@(future (dosync (Thread/sleep 1000) @a))
;;500
(ref-history-count a)
;;8

(def a (ref 0 :min-history 50 :max-history 100))
(future (dotimes [_ 500] (dosync (Thread/sleep 20) (alter a inc))))

@(future (dosync (Thread/sleep 1000) @a))
;;43
(ref-history-count a)
;;50

;write skew
;如果事务要读取的ref值在事务进行到一半的时候在别的事务中被修改了，而事务依赖的还是那个旧的值，那么当事务提交的时候，整个状态就不一致了，这种情况称为write skew

;使用ensure可以保证读取的值是最新的值