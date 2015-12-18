;; replace
;; (replace s match replacement)

;; Replaces all instance of match with replacement in s.
;;   match/replacement can be:
;;   string / string
;;  char / char
;;  pattern / (string or function of match).
;;   See also replace-first.
;;   The replacement is literal (i.e. none of its characters are treated
;;  specially) for all cases above except pattern / string.
;;   For pattern / string, $1, $2, etc. in the replacement string are
;;  substituted with the string that matched the corresponding
;;  parenthesized group in the pattern.  If you wish your replacement
;;  string r to be used literally, use (re-quote-replacement r) as the
;;  replacement argument.  See also documentation for
;;  java.util.regex.Matcher's appendReplacement method.
;;   Example:
;;  (clojure.string/replace "Almost Pig Latin" #"\b(\w)(\w+)\b" "$2$1ay")
;;  -> "lmostAay igPay atinLay"

(use '[clojure.string :only (replace)])
(replace "The color is red" #"red" "blue")
;; => "The color is blue"

(replace "The color is red" #"[auoie]" #(str %1 %1))
;; => Thee cooloor iis reed"

;; Note: When replace-first or replace have a regex pattern as their
;; match argument, dollar sign ($) and backslash (\) characters in
;; the replacement string are treated specially.


;; Example: first substring that the pattern matches is "fodder", with
;; (o+) matching "o" and (\S+) matching "dder".  Replacement string
;; says to replace the entire match "fodder" with $2, the string
;; matched by the second parenthesized group, "dder", followed by $1,
;; "o".
(replace "fabulous fodder foo food" #"f(o+)(\S+)" "$2$1")
;; => "fabulous ddero oo doo"

;; To avoid this special treatment of $ and \, you must escape them with
;; \.  Because it is in a Clojure string, to get one \ we must escape
;; *that* with its own \.
(replace "fabulous fodder foo food" #"f(o+)(\S+)" "\\$2\\$1")
;; fabulous $2$1 $2$1 $2$1

