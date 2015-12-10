;; amap
;; (amap a idx ret expr)
;; Maps an expression across an array a, using an index named idx, and
;; return value named ret, initialized to a clone of a, then setting
;; each element of ret to the evaluation of expr, returning the new
;; array ret.
;; amap有4个参数：源数组、给下标命名的名称、给结果数组命名的名称（初始化为源数组的拷贝），一个表达式

(let [a (int-array (range 10))]
  (amap a i res
        (inc (aget a i))))
(seq (let [a (int-array (range 10))]
  (amap a i res
        (inc (aget a i)))))
