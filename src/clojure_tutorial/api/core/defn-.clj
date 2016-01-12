;; defn-
;; (defn- name & decls)
;; same as defn, yielding non-public def

(defn- foo []
         "World!")

(defn bar []
       (str "Hello " (foo)))

(in-ns 'foo)

(user/bar)
;; => "Hello World!"
(user/foo)
;; => clojure.lang.Compiler$CompilerException: java.lang.IllegalStateException:
