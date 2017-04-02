(ns sierpinski-sieve.core
  (:require [devtools.core :as devtools]
            [reagent.core :as reagent :refer [atom]]
            [sierpinski-sieve.blit :as blit])
  (:use-macros [sierpinski-sieve.macros :only [forloop]]))

(enable-console-print!)
(defn log [& args] (.log js/console args))

(defonce app-state (atom {}))

(defn next-row [modulus row]
  (let [size (inc (alength row))
        next (make-array size)]
    (aset next 0 1)
    (aset next (dec size) 1)
    (forloop [(x 1) (< x (dec size)) (inc x)]
      (let [before (aget row (dec x))
            above (aget row x)]
        (aset next x (mod (+ before above) modulus))))
    next))

(defn sieve [initial-state modulus]
  (iterate (partial next-row modulus) (into-array initial-state)))

(defn paint-row [ctx y row size]
  (forloop [(x 0) (< x (alength row)) (inc x)]
     (let [disp-x (+ (* (- size y) 0.5) x)
           value (aget row x)]
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
    [:center
     [:h1 "Sierpinski Triangle"]
     [:canvas {:width size :height size :id "canvas"}]
     [:p
      "© 2017 Charles L.G. Comstock "
      [:a {:href "https://github.com/dgtized/sierpinski-sieve"} "(github)"]]]))

(defn main [size]
  (reagent/render [(render-canvas size)]
                  (. js/document (getElementById "app")))
  (time
   (let [triangle (time (doall (take size (sieve '(1) 2))))]
     (time (paint "canvas" size triangle)))))

(main 1000)

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
