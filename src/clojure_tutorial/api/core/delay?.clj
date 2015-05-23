(ns clojure-tutorial.api.core.delay?)

;delay?
;(delay? x)

;returns true if x is a Delay created with delay

(def v (delay (do
                (println "starting...")
                (Thread/sleep 5000)
                10)))
;或
(def v (delay (println "starting...")
              (Thread/sleep 5000)
              10))
(delay? v)                                                  ;;true


@v
;;starting...
;;10
