;; re-find
;; (re-find m)
;; (re-find re s)

;; Returns the next regex match, if any, of string to pattern, using
;; java.util.regex.Matcher.find().  Uses re-groups to return the
;; groups.


(def *matcher* (re-matcher #"\d+" "abc12345def"))

(re-find *matcher*)
;; => 12345

(re-find *matcher*)
;; => nil


;; If you only want the first match, it is shorter to call re-find with the
;; pattern and the string to search, rather than explicitly creating a matcher
;; as above.
(re-find #"\d+" "abc12345def")
;; => 12345


;; re-find can be used to iterate through re matches in the string
(def phone-number "672-345-456-3212")
(def matcher (re-matcher #"\d+" phone-number))
(re-find matcher)
;; => 672
(re-find matcher)
;; => 345
(re-find matcher)
;; => 456
(re-find matcher)
;; 3212
(re-find matcher)
;; nil


(re-find #"^left-" "left-eye")
;; =>lfet-

(re-find #"^left-" "cleft-chin")
;; => nil

(re-find #"^left-" "wongleblart")
;; => nil
