(ns clojure-tutorial.api.core.concat)

;concat
;    (concat)(concat x)(concat x y)(concat x y & zs)
;Returns a lazy seq representing the concatenation of the elements in the supplied colls.

(concat [1 2] [3 4])                                        ;;(1 2 3 4)
(concat [:a :b] nil [1 [2 3] 4])                            ;;(:a :b 1 [2 3] 4)
(concat "abc" "def")                                        ;;(\a \b \c \d \e \f)