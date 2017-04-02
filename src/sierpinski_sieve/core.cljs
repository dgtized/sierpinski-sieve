(ns sierpinski-sieve.core
  (:require [devtools.core :as devtools]
            [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(devtools/install! [:formatters :hints])
(defn log [& args] (.log js/console args))

(defonce app-state (atom {}))

(defn render-canvas []
  (let [size 400]
    [:center [:canvas {:width size :height size :id "canvas"}]]))

(defn next-row [modulus row]
  (for [x (range 0 (inc (count row)))]
    (let [before (nth row (dec x) 0)
          above (nth row x 0)]
      (mod (+ before above) modulus))))

(defn sieve [initial-state modulus]
  (iterate (partial next-row modulus) initial-state))

(defn paint-row [ctx y row size]
  (doseq [[x value] (map-indexed vector row)]
    (let [disp-x (+ (* (- size y) 0.5) x)]
      (if (odd? value)
        (.rect ctx disp-x y 1 1)))))

(defn paint [canvas-id size sieve]
  (when-let [canvas-element (. js/document getElementById canvas-id)]
    (when-let [ctx (.getContext canvas-element "2d")]
      (do
        (set! (.-fillStyle ctx) "black")
        (.fillRect ctx 0 0 size size)
        (set! (.-fillStyle ctx) "white")
        (doseq [[y row] (reverse (map-indexed vector sieve))]
          (paint-row ctx y row size))
        (.fill ctx)))))

(reagent/render [render-canvas]
                (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (time (paint "canvas" 400 (take 400 (sieve '(1) 2))))
)
