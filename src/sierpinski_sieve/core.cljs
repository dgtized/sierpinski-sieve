(ns sierpinski-sieve.core
  (:require [devtools.core :as devtools]
            [reagent.core :as reagent :refer [atom]]
            [sierpinski-sieve.blit :as blit]))

(enable-console-print!)

(devtools/install! [:formatters :hints])
(defn log [& args] (.log js/console args))

(defonce app-state (atom {}))

(defn next-row [modulus row]
  (let [size (inc (count row))
        next (make-array size)]
    (aset next 0 1)
    (aset next (dec size) 1)
    (doseq [x (range 1 (dec size))]
      (let [before (aget row (dec x))
            above (aget row x)]
        (aset next x (mod (+ before above) modulus))))
    next))

(defn sieve [initial-state modulus]
  (iterate (partial next-row modulus) (into-array initial-state)))

(defn paint-row [ctx y row size]
  (doseq [[x value] (map-indexed vector row)]
    (let [disp-x (+ (* (- size y) 0.5) x)]
      (if (odd? value)
        (.fillRect ctx disp-x y 1 1)))))

(defn paint [canvas-id size sieve]
  (when-let [canvas-element (. js/document getElementById canvas-id)]
    (when-let [ctx (.getContext canvas-element "2d")]
      (do
        (set! (.-fillStyle ctx) "black")
        (.fillRect ctx 0 0 size size)
        (set! (.-fillStyle ctx) "white")
        (doseq [[y row] (map-indexed vector sieve)]
          (paint-row ctx y row size))))))

(defn render-canvas [size]
  (fn []
    [:center [:canvas {:width size :height size :id "canvas"}]]))

(defn main [size]
  (reagent/render [(render-canvas size)]
                  (. js/document (getElementById "app")))
  (time
   (let [triangle (time (doall (take size (sieve '(1) 2))))]
     (time (paint "canvas" size triangle)))))

(main 325)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
