(ns clojure-tutorial.api.core.add-watch)
;add-watch
;(add-watch reference key fn)

;Adds a watch function to an agent/atom/var/ref reference.
; The watch fn must be a fn of 4 args: a key, the reference, its old-state, itsnew-state.
; Whenever the reference's state might have been changed, any registered watches will have their functions called.
; The watch fn will be called synchronously, on the agent's thread if an agent, before any pending sends if agent or ref.
; Note that an atom's or ref's state may have changed again prior to the fn call, so use old/new-state rather than derefing the reference.
; Note also that watch fns may be called from multiple threads simultaneously.
; Var watchers are triggered only by root binding changes, not thread-local set!s.
; Keys must be unique per reference, and can be used to remove the watch with remove-watch, but are otherwise considered opaque by the watch mechanism.

(defn echo-watch
  [key identity old new]
  (println key old "=>" new))
(def sarah (atom {:name "Sarah" :age 25}))
(add-watch sarah :echo echo-watch)
;;#<Atom@3942d14f: {:age 25, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 25, :name Sarah} => {:age 26, :name Sarah}
;;{:age 26, :name "Sarah"}
(add-watch sarah :echo2 echo-watch)
;;#<Atom@3942d14f: {:age 26, :name "Sarah"}>
(swap! sarah update-in [:age] inc)
;;:echo {:age 26, :name Sarah} => {:age 27, :name Sarah}
;;:echo2 {:age 26, :name Sarah} => {:age 27, :name Sarah}
;;{:age 27, :name "Sarah"}