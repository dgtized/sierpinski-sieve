(ns sierpinski-sieve.blit)

;; tried to use putImageData to blit out data
;; doesn't seem to be significantly faster

(defn paint-row [pixel-data y row size]
  (let [y-offset (* y size)]
    (doseq [[x value] (map-indexed vector row)]
      (let [disp-x (+ (int (* (- size y) 0.5)) x)
            p (* (+ y-offset disp-x) 4)]
        (when (odd? value)
          (aset pixel-data (+ p 3) 0))))))

(defn paint [canvas-id size sieve]
  (when-let [canvas-element (.getElementById js/document canvas-id)]
    (when-let [ctx (.getContext canvas-element "2d")]
      (let [pixels (.createImageData ctx size size)
            pixel-data (.-data pixels)]
        (doseq [i (range 0 (* size size))]
          (aset pixel-data (+ (* i 4) 3) 255))
        (doseq [[y row] (map-indexed vector sieve)]
          (paint-row pixel-data y row size))
        (.putImageData ctx pixels 0 0)))))
