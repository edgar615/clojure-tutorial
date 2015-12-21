;take
;(take n coll)

;Returns a lazy sequence of the first n items in coll, or all items if there are fewer than n.

(take 3 '(1 2 3 4 5 6))                                     ;;(1 2 3)
(take 3 [1 2 3 4 5 6])                                      ;;(1 2 3)
(take 3 [1 2])                                              ;;(1 2)
(take 1 [])                                                 ;;()
(take 1 nil)                                                ;;()
(take 0 [1])                                                ;;()
(take -1 [1])                                               ;;()

(take 3 [1 2 3 4 5 6 7 8 9 10])
;; => (1 2 3)

(drop 3 [1 2 3 4 5 6 7 8 9 10])
;; => (4 5 6 7 8 9 10)
