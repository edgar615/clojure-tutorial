(ns clojure-tutorial.api.core.re-seq)

;re-seq
;(re-seq re s)

;Returns a lazy sequence of successive matches of pattern in string,
;using java.util.regex.Matcher.find(), each such match processed with re-groups.

(re-seq #"\d" "clojure.1.6.0")                              ;;("1" "6" "0")
(re-seq #"\w+" "mary had a little lamb")                   ;;("mary" "had" "a" "little" "lamb")

(re-seq #"(\S+):(\d+)" " RX pkts:18 err:5 drop:48")
;;(["pkts:18" "pkts" "18"] ["err:5" "err" "5"] ["drop:48" "drop" "48"])

