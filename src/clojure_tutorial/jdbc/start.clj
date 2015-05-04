(ns clojure-tutorial.jdbc.start)

(require '[clojure.java.jdbc :as j])

;Setting Up A Data Source
(def mysql-db {:classname   "com.mysql.jdbc.Driver"
               :subprotocol "mysql"
               :subname     "//10.4.7.15:3306/clojure_test"
               :user        "root"
               :password    "csst"})

(j/insert! mysql-db :fruit
           {:name "Apple" :appearance "rosy" :cost 24}
           {:name "Orange" :appearance "round" :cost 49})

(j/query mysql-db
         ["select * from fruit where appearance = ?" "rosy"]
         :row-fn :cost)

(def rosy-fruit (j/query mysql-db
                         ["select * from fruit where appearance = ?" "rosy"]
                         :row-fn :cost))

;Reusing Connections
;Grouping Operations using with-db-connection - If you don't want to deal with a connection pooling library, you use this macro to automatically open a connection and maintain it for a body of code.
(j/with-db-connection [db-con mysql-db]
                      (j/insert! db-con :fruit
                                 {:name "Apple" :appearance "rosy" :cost 24}
                                 {:name "Orange" :appearance "round" :cost 49})
                      (j/query db-con
                               ["select * from fruit where appearance = ?" "rosy"]
                               :row-fn :cost))

;Connection Pooling - This is the recommended approach and is fairly straightforward, with a number of connection pooling libraries available.
; TODO

;Manipulating Data With SQL
(j/query mysql-db ["select * from fruit"])
;You can follow the SQL string with any parameters for that SQL:
(j/query mysql-db ["select name, cost from fruit where appearance = ?" "rosy"])
;The query function returns a fully-realized sequence of rows from the database. Under the hood, query converts the JDBC ResultSet into a (lazy) sequence of rows and then realizes that sequence.
;1.You can process each row inside the query function by passing :row-fn f. This will call f on each row as the underlying ResultSet is processed. The result of query will be the sequence of the result of f applied to each row in turn. The default for :row-fn is identity.
(j/query mysql-db ["select name, cost from fruit where appearance = ?" "rosy"] :row-fn :cost)
(println (j/query mysql-db ["select name, cost from fruit where appearance = ?" "rosy"]))
(println (j/query mysql-db ["select name, cost from fruit where appearance = ?" "rosy"] :row-fn :cost))
;2.You can also process the entire ResultSet inside the query function by passing :result-set-fn g. This will call g on the entire (lazy) sequence of processed rows. The result of query will be the result of that call to g. To avoid the connection being closed before the result of query is fully consumed, g should be an eager function. The default for :result-set-fn is doall.
; TODO
;By default, query converts all of the column names in the ResultSet to lowercase keywords in the maps. This can be controlled by an optional :identifiers argument which is described, along with other options for query
;TODO

;CRUD
;(jdbc/insert! db-spec :table {:col1 42 :col2 "123"}) ;; Create
;(jdbc/query!  db-spec ["SELECT * FROM table WHERE id = ?" 13]) ;; Read
;(jdbc/update! db-spec :table {:col1 77 :col2 "456"} ["id = ?" 13]) ;; Update
;(jdbc/delete! db-spec :table ["id = ?" 13]) ;; Delete
;insert
;insert! can take multiple maps to insert multiple rows. It can also take a vector of column names (as strings or keywords), followed by one or more vectors of column values to insert into those respective columns, much like an INSERT statement in SQL. Entries in the map that have the value nil will cause NULL values to be inserted into the corresponding columns.
(j/insert! mysql-db :fruit {:name "banana" :appearance "unkown" :cost 30})

;query
(j/query mysql-db ["select * from fruit where name = ?" "banana"])

;update
;update! takes a map of columns to update, with their new values, and a SQL clause used to select which rows to update (prepended by WHERE in the generated SQL). As with insert!, nil values in the map cause the corresponding columns to be set to NULL.
(j/update! mysql-db :fruit {:appearance "Unkown" :cost 15} ["name = ?" "banana"])


;delete
;delete! takes a SQL clause used to select which rows to delete, just like update!.
(j/delete! mysql-db :fruit ["name = ?" "banana"])

;By default, the table name and column names are used as-is in the underlying SQL. That can be controlled by an optional :entities argument which is described in Using SQL.
(j/execute! mysql-db ["delete from fruit where name = ?" "Apple"])


;Manipulating Tables With DDL
;java.jdbc provides create-table-ddl and drop-table-ddl to generate basic CREATE TABLE and DROP TABLE DDL strings. Anything beyond that can be constructed manually as a string. DDL can be executed using db-do-commands:
(j/db-do-commands mysql-db
                  (j/create-table-ddl :fruit2
                                      [:name "varchar(32)"]
                                      [:appearance "varchar(32)"]
                                      [:cost :int]))
(j/db-do-commands mysql-db "CREATE INDEX I_NAME ON fruit2(name)")

;DDL operations can be executed using the db-do-commands function. The general approach is:
;(j/db-do-commands db-spec sql-command-1 sql-command-2 .. sql-command-n)
;The commands are executed as a single, batched statement, wrapped in a transaction. If you want to avoid the transaction, use this approach:
;(j/db-do-commands db-spec false sql-command-1 sql-command-2 .. sql-command-n)

(j/db-do-commands mysql-db
                  (j/drop-table-ddl :fruit2 :entities clojure.string/upper-case))

;Accessing metadata
;TODO

;Reading rows
;To obtain a fully realized result set as a sequence of maps, you can use query with a vector containing the SQL string and any parameters needed by the SQL:
(j/query mysql-db ["select * from fruit"])
;; ({:id 1 :name "Apple" :appearance "red" :cost 59 :grade 87}
;;  {:id 2 :name "Banana" :appearance "yellow" :cost 29 :grade 92.2}
;;  ...)
(j/query mysql-db ["select * from fruit where cost < ?" 30])
;You can also return the result set as a sequence of vectors. The first vector will contain the column names, and each subsequent vector will represent a row of data with values in the same order as the columns.
(j/query mysql-db ["select * from fruit"] :as-arrays? true)
;; ([:id :name :appearance :cost :grade]
;;  [2 "Banana" "yellow" 29 92.2]
;;  ...)


;Processing a result set lazily
;Since query returns a fully realized result set, it can be difficult to process very large results. Fortunately java.jdbc provides a way to process a large result set lazily while the connection is open, by passing a function via the :result-set-fn option. Note that the function you pass must force realization of the result to avoid the connection closing while the result set is still being processed. A reduce-based function is a good choice.
(j/query mysql-db ["select * from fruit where cost < ?" 50]
         :result-set-fn (fn [rs]
                          (reduce (fn [i m]
                                    (+ i (:cost m)))
                                  0 rs)))
;等同于
(j/query mysql-db ["SELECT SUM(cost) FROM fruit WHERE cost < ?" 50]
         :result-set-fn first)                              ;;{:sum(cost) 1078M}
;With :result-set-fn, we can process very large result sets because the rows are fetched from the database in chunks, as your function realizes the result set sequence.

;Remember that if you also specify :as-arrays? true, your result set function will be passed a sequence of vectors in which the first vector contains the column names and subsequent vectors represent the values in the rows, matching the order of the column names.

;Processing each row lazily
;In addition to processing the entire result set, we can also process each row with the :row-fn option. Again, we pass a function but this time it will be invoked on each row, as the result set is realized.
(j/query mysql-db ["select * from fruit"] :row-fn :name)
;The result is still a fully realized sequence, but each row has been transformed by the :name function you passed in.

;You can combine this with :result-set-fn to simplify processing of result sets:
(j/query mysql-db ["select * from fruit"]
         :row-fn :cost
         :result-set-fn (partial reduce +))

;等同于
(j/query mysql-db ["SELECT SUM(cost) AS total FROM fruit WHERE cost < ?" 50]
         :row-fn :total
         :result-set-fn first)

(defn add-tax [row] (assoc row :tax (* 0.08 (:cost row))))

(j/query mysql-db ["SELECT * FROM fruit"]
         :row-fn add-tax)

;Inserting data
;Inserting a row
;If you want to insert a single row (or partial row) and get back the generated keys, you can use insert! and specify the columns and their values as a map. This performs a single insert statement. A single-element sequence containing a map of the generated keys will be returned.
(j/insert! mysql-db :fruit {:name "Pear" :appearance "green" :cost 99})
;;({:generated_key 23})
(println (j/insert! mysql-db :fruit {:name "Pear" :appearance "green" :cost 99}))
;Inserting multiple rows
(j/insert! mysql-db :fruit {:name "Pomegranate" :appearance "fresh" :cost 585}
           {:name "Kiwifruit" :cost 93})
;;({:generated_key 26} {:generated_key 27})
;If you use insert! and specify the columns you wish to insert followed by each row as a vector of column values, then you must specify the same columns in each row, and you will not get generated keys back, just row counts. If you wish to insert complete rows, you may omit the column name vector (passing nil instead) but your rows must match the natural order of columns in your table so be careful!
(j/insert! mysql-db :fruit
           [:name :appearance :cost]
           ["Apple" "red" 59]
           ["Banana" "yellow" 29]
           ["Peach" "fuzzy" 139]
           ["Orange" "juicy" 89])
;;(1 1 1 1)


;Updating rows
;If you want to update simple column values in one or more rows based on a simple SQL predicate, you can use update! with a map, representing the column values to set, and a SQL predicate with parameters. If you need a more complex form of update, you can use the execute! function with arbitrary SQL (and parameters).
(j/update! mysql-db :fruit
           {:cost 60}
           ["name = ?" "Apple"])
(j/execute! mysql-db ["update fruit set cost = cost * 2 where name = ?" "Banana"])

;Deleting rows
;If you want to delete any rows from a table that match a simple predicate, the delete! function can be used.
(j/delete! mysql-db :fruit ["cost < ?" 60])
(j/execute! mysql-db ["delete from fruit where name = ?" "Orange"])

;Using transactions
(j/with-db-transaction [db-con mysql-db]
                       (j/update! db-con :fruit
                                  {:cost 100}
                                  ["name = ?" "Apple"])
                       (j/delete! db-con :fruit ["cost = ?" 100]))
;You can specify the transaction isolation level as part of the with-db-transction binding:
;(j/with-db-transaction [t-con db-spec :isolation :serializable]...)
;Possible values for :isolation are :none, :read-committed, :read-uncommitted, :repeatable-read, and :serializable. Be aware that not all databases support all isolation levels.
;(j/db-set-rollback-only! t-con)   ; this transaction will rollback instead of commit
;(j/db-unset-rollback-only! t-con) ; this transaction commit if successful
;(j/db-is-rollback-only t-con)     ; returns true if transaction is set to rollback
(j/with-db-transaction [t-con mysql-db]
                       (j/db-set-rollback-only! t-con)
                       (j/insert! t-con :fruit {:name "Apple2" :appearance "green" :cost 99}))

;Updating or Inserting rows conditionally
(defn update-or-insert!
  "Updates columns or inserts a new row in the specified table"
  [db table row where-clause]
  (j/with-db-transaction [t-con db]
                         (let [result (j/update! t-con table row where-clause)]
                           (if (zero? (first result))
                             (j/insert! t-con table row)
                             result))))

(update-or-insert! mysql-db :fruit
                   {:name "Cactus" :appearance "Spiky" :cost 2000}
                   ["name = ?" "Cactus"])
;; inserts Cactus (assuming none exists)
(update-or-insert! mysql-db :fruit
                   {:name "Cactus" :appearance "Spiky" :cost 2500}
                   ["name = ?" "Cactus"])

;Exception Handling and Transaction Rollback
;(j/with-db-transaction [t-con mysql-db]
;                       (j/insert! t-con :fruit
;                                  [:name :appearance]
;                                  ["Grape" "yummy"]
;                                  ["Pear" "bruised"])
;                       (throw (Exception. "sql/test exception")))


(j/with-db-transaction [t-con mysql-db]
                       (prn "is-rollback-only" (j/db-is-rollback-only t-con))
                       ;; is-rollback-only false
                       (j/db-set-rollback-only! t-con)
                       ;; the following insert will be rolled back when the transaction ends:
                       (j/insert! t-con :fruit
                                  [:name :appearance]
                                  ["Grape2" "yummy"]
                                  ["Pear2" "bruised"])
                       (prn "is-rollback-only" (j/db-is-rollback-only t-con))
                       ;; is-rollback-only true
                       ;; the following will display the inserted rows:
                       (j/query t-con ["SELECT * FROM fruit"]
                                :row-fn println))
(prn)
;; outside the transaction, the following will show the original rows
;; without those two inserted inside the (rolled-back) transaction:
(j/query mysql-db ["SELECT * FROM fruit"]
         :row-fn println)

;Clojure identifiers and SQL entities
;As hinted at above, java.jdbc converts SQL entity names in result sets to keywords in Clojure by making them lowercase, and converts strings and keywords that specify table and column names (in maps) to SQL entities as-is by default.

;You can override this behavior by specifying :identifiers on the query and metadata-result functions and by specifying :entities on the delete!, insert!, update!, create-table-ddl, and drop-table-ddl functions.

;:identifiers is for converting ResultSet column names to keywords. It defaults to clojure.string/lower-case.
;:entities is for converting Clojure keywords/string to SQL entity names. It defaults to identity.
;(j/query mysql-db ["SELECT * FROM mixedTable"]
;         :identifiers identity)

;(j/query mysql-db ["SELECT * FROM mixedTable"]
;         :identifiers #(.replace % \_ \-))
;For several databases, you will often want entities to be quoted in some way (sometimes referred to as "stropping"). A utility function quoted is provided that accepts either a single character or a vector pair of characters, and returns a function suitable for use with the :entities option.
(j/insert! mysql-db :fruit
           {:name "Apple" :appearance "Round" :cost 99}
           :entities (j/quoted \`))
;INSERT INTO `fruit` ( `name`, `appearance`, `cost` ) VALUES ( ?, ?, ? )
;with the parameters "Apple", "Round", "99"

(j/insert! mysql-db :fruit
           {:name "Apple" :appearance "Round" :cost 99}
           :entities (j/quoted [\[ \]]))
;INSERT INTO [fruit] ( [name], [appearance], [cost] ) VALUES ( ?, ?, ? )
;with the parameters "Apple", "Round", "99"


;Protocol extensions for transforming values
;TODO