(ns sierpinski-sieve.core
  (:require [devtools.core :as devtools]
            [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)
(devtools/install!)

(defonce app-state (atom {:text "Hello world!"}))

(defn hello-world []
  [:h1 (:text @app-state)])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
