;; def
;; Creates and interns or locates a global var with the name of symbol and a
;; namespace of the value of the current namespace (*ns*). See
;; http://clojure.org/special_forms for more information.

(def my-var 5)
my-var
;; 5

(def my-function (fn [x] (* x x x )))

(my-function 5)
;; 125

(def ted-nugent "The nuge rocks" 123)

(doc ted-nugent)
;; -------------------------
;; clojure-noob.core/ted-nugent
;;   The nuge rocks
;; nil


ted-nugent
;; 123

(def severity :mild)
(def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
(if (= severity :mild)
  (def error-message (str error-message "MILDLY INCONVENIENCED!"))
  (def error-message (str error-message "DOOOOOOOMED!")))
