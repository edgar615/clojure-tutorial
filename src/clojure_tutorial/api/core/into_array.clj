(ns clojure-tutorial.api.core.into-array)

;into-array
;    (into-array aseq)(into-array type aseq)

;Returns an array with components set to the values in aseq. The array's component type is type if provided, or the type of the first value in aseq if present, or Object.
;All values in aseq must be compatible with the component type. Class objects for the primitive types can be obtained using, e.g., Integer/TYPE.

;; Array's component type is set to (class 2), cannot add Strings.
;; This will result in an IllegalArgumentException
(into-array [2 "a" "b" 3])                                  ;;IllegalArgumentException array element type mismatch  java.lang.reflect.Array.set (Array.java:-2)

(into-array Object [2 "a" "b" 3])                           ;;#<Object[] [Ljava.lang.Object;@56ce9cb0>
(into-array (range 4))                                      ;;#<Long[] [Ljava.lang.Long;@3827b0b>

(into-array Byte/TYPE (range 4))                            ;;#<byte[] [B@198ff828>

(into-array Byte/TYPE (map byte (range 4)))                 ;;#<byte[] [B@5af9f2d4>

