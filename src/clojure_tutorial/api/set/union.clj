(ns clojure-tutorial.api.set.union)


(clojure.set/union #{:a :b :d} #{:b :c :d})                 ;#{:c :b :d :a}