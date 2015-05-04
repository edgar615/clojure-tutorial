(ns clojure-tutorial.jdbc.yesql.yesql)

(require '[yesql.core :refer [defquery]])
(require '[clojure.java.jdbc :as j])

(defquery fruit-by-name "clojure_tutorial/jdbc/yesql/where/fruit_by_name.sql")

(def mysql-db {:classname   "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname     "//10.4.7.15:3306/clojure_test"
               :user        "root"
               :password    "csst"})

(fruit-by-name mysql-db "Apple")
;;({:count 5})
; Use it in a clojure.java.jdbc transaction.
(j/with-db-transaction [conn mysql-db]
                       {:apple (fruit-by-name conn "Apply")
                        :Orange (fruit-by-name conn "Orange")})

;One File, Many Queries
;As an alternative to the above, you can have many SQL statements in a single SQL file. The file format is: (<name tag> [docstring comments] <the query>)*,
(require '[yesql.core :refer [defqueries]])

(defqueries "clojure_tutorial/jdbc/yesql/where/queryfile.sql")


(fruit-count mysql-db)
;;({:count 5})
; Use it in a clojure.java.jdbc transaction.
(j/with-db-transaction [conn mysql-db]
                       {:apple (fruit-by-name conn "Apply")
                        :Orange (fruit-by-name conn "Orange")})


;IN-list Queries
(find-fruits mysql-db [400 401 402 403 404 404] 30)

c

(find-fruits mysql-db [400 401 402 403 404 404] 30)