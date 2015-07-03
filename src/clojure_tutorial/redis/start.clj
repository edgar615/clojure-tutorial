(ns clojure-tutorial.redis.start
  (:require [taoensso.carmine :as car :refer (wcar)]))

;Connections
(def server1-conn {:pool {} :spec {:host "127.0.0.1" :port 6379}})

(defmacro war* [& body] `(car/wcar server1-conn ~@body))

(war* (car/ping)
      (car/set "foo" "bar")
      (car/get "foo"))

;;clojure.lang.ExceptionInfo: WRONGTYPE Operation against a key holding the wrong kind of value
;(war* (car/spop "foo"))

;Serialization

;The only value type known to Redis internally is the byte string.
; But Carmine uses Nippy under the hood and understands all of Clojure's rich datatypes, letting you use them with Redis painlessly:

(war* (car/set "cli-key" {:bigint (bigint 31415926535897932384626433832795)
                          :vec    (vec (range 5))
                          :set    #{true false :a :b :c :d}
                          :bytes  (byte-array 5)
                          ;;...
                          })
      (car/get "cli-key"))
;;[OK {:bigint 31415926535897932384626433832795N, :vec [0 1 2 3 4], :set #{true :c false :b :d :a}, :bytes #<byte[] [B@36a7586f>}]

;Types are handled as follows:
;
;Clojure strings become Redis strings.
;Keywords become Redis strings. (v2+)
;Simple Clojure numbers (integers, longs, floats, doubles) become Redis strings.
;Everything else gets automatically de/serialized.
;
;You can force automatic de/serialization for an argument of any type by wrapping it with car/serialize.


;lua
;TODO

;Helpers