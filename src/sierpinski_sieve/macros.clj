(ns sierpinski-sieve.macros)

;; from https://github.com/swannodette/chambered/blob/master/src/chambered/macros.clj
(defmacro forloop [[init test step] & body]
  `(loop [~@init]
     (when ~test
       ~@body
       (recur ~step))))
