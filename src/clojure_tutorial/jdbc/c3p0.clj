(ns clojure-tutorial.jdbc.c3p0
  (:import com.mchange.v2.c3p0.ComboPooledDataSource))

(require '[clojure.java.jdbc :as j])

(defn pool
  [spec]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setDriverClass (:classname spec))
               (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
               (.setUser (:user spec))
               (.setPassword (:password spec))
               ;; expire excess connections after 30 minutes of inactivity:
               (.setMaxIdleTimeExcessConnections (* 30 60))
               ;; expire connections after 3 hours of inactivity:
               (.setMaxIdleTime (* 3 60 60)))]
    {:datasource cpds}))

;Now you can create a single connection pool:
(def db-spec {:classname   "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname     "//10.4.7.15:3306/clojure_test"
               :user        "root"
               :password    "csst"})
(def pooled-db (delay (pool db-spec)))
(defn db-connection [] @pooled-db)
(j/insert! @pooled-db :fruit
           {:name "Apple" :appearance "rosy" :cost 24}
           {:name "Orange" :appearance "round" :cost 49})
;And then call (db-connection) wherever you need access to it. If you're using a component lifecycle for your application, such as Stuart Sierra has advocated, you won't need pooled-db or db-connection, you'll just create (pool db-spec) as part of your application's initialization and pass it around as part of your system configuration
(def pooled-db (pool db-spec))
(j/insert! pooled-db :fruit
           {:name "Apple" :appearance "rosy" :cost 24}
           {:name "Orange" :appearance "round" :cost 49})