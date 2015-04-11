(ns clojure-tutorial.api.core.find)

;find
;(find map key)

;Returns the map entry for key, or nil if key not present.

(find {:a 1 :b 2 :c 3} :a)                                  ;;[:a 1]
(find {:a 1 :b 2 :c 3} :d)                                  ;;nil

(find [:a :b :c] 1)                                         ;;[1 :b]
(find [:a :b :c] 5)                                         ;;nil

(find {:ethel nil} :lucy)                                   ;;nil
(find {:ethel nil} :ethel)                                  ;;[:ethel nil]
;find可以很容易跟解构形式如if-let、when-let一起使用
(if-let [e (find {:a 5 :b 6} :a)]
  (format "found %s => %s" (key e) (val e))
  "not-found")                                              ;;"found :a => 5"
(if-let [[k v] (find {:a 5 :b 6} :a)]
  (format "found %s => %s" k v)
  "not-found")                                              ;;"found :a => 5"
