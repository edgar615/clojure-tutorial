;; ns
;; (ns name docstring? attr-map? references*)
;; Sets *ns* to the namespace named by name (unevaluated), creating it
;; if needed.  references can be zero or more of: (:refer-clojure ...)
;; (:require ...) (:use ...) (:import ...) (:load ...) (:gen-class)
;; with the syntax of refer-clojure/require/use/import/load/gen-class
;; respectively, except the arguments are unevaluated and need not be
;; quoted. (:gen-class ...), when supplied, defaults to :name
;; corresponding to the ns name, :main true, :impl-ns same as ns, and
;; :init-impl-ns true. All options of gen-class are
;; supported. The :gen-class directive is ignored when not
;; compiling. If :gen-class is not supplied, when compiled only an
;; nsname__init.class will be generated. If :refer-clojure is not used, a
;; default (refer 'clojure.core) is used.  Use of ns is preferred to
;; individual calls to in-ns/require/use/import:
;;  (ns foo.bar
;;   (:refer-clojure :exclude [ancestors printf])
;;   (:require (clojure.contrib sql combinatorics))
;;   (:use (my.lib this that))
;;   (:import (java.util Date Timer Random)
;;            (java.sql Connection Statement)))

;;     (:refer-clojure)
;;     (:require)
;;     (:use)
;;     (:import)
;;     (:load)
;;     (:gen-class)


;; (:require) works a lot like the require function. For example, this:
(ns the-divine-cheese-code.core
  (:require the-divine-cheese-code.visualization.svg))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)

;; You can also alias a library that you require within ns, just like when you call the function. This:
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(require ['the-divine-cheese-code.visualization.svg :as 'svg])

;; You can require multiple libraries in a (:require) reference as follows. This:

(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]
            [clojure.java.browse :as browse]))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(require ['the-divine-cheese-code.visualization.svg :as 'svg])
(require ['clojure.java.browse :as 'browse])

;; However, one difference between the (:require) reference and the require function is that
;; the reference also allows you to refer names. This:
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :refer [points]]))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg :only ['points])

;; You can also refer all symbols (notice the :all keyword):
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :refer :all]))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)


;; This is the preferred way to require code, alias namespaces, and refer symbols. It’s recommended that you not use (:use), but since it’s likely that you’ll come across it, it’s good to know how it works. You know the drill. This:

(ns the-divine-cheese-code.core
  (:use clojure.java.browse))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(use 'clojure.java.browse)

(ns the-divine-cheese-code.core
  (:use [clojure.java browse io]))
;; 等同于
(in-ns 'the-divine-cheese-code.core)
(use 'clojure.java.browse)
(use 'clojure.java.io)

