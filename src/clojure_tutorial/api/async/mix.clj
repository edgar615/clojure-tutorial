;; mix
;; (mix out)
;; Creates and returns a mix of one or more input channels which will
;; be put on the supplied out channel. Input sources can be added to
;; the mix with 'admix', and removed with 'unmix'. A mix supports
;; soloing, muting and pausing multiple inputs atomically using
;; 'toggle', and can solo using either muting or pausing as determined
;; by 'solo-mode'.
;;  Each channel can have zero or more boolean modes set via 'toggle':
;;  :solo - when true, only this (ond other soloed) channel(s) will appear
;;         in the mix output channel. :mute and :pause states of soloed
;;         channels are ignored. If solo-mode is :mute, non-soloed
;;         channels are muted, if :pause, non-soloed channels are
;;         paused.
;;  :mute - muted channels will have their contents consumed but not included in the mix
;; :pause - paused channels will not have their contents consumed (and thus also not included in the mix)

(require '[clojure.core.async :as async :refer :all])

(def ch-out (chan))

(def mix-out (mix ch-out))

(def ch-example1 (chan))

(def ch-example2 (chan))

(admix mix-out ch-example1)

(admix mix-out ch-example2)

(put! ch-example1 "sent to chan 1")

(put! ch-example2 "sent to chan 2")

(<!! ch-out)
;; "sent to chan 1"

(<!! ch-out)
;; "sent to chan 2"
