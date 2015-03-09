(ns zupa-wars.terrain
  (:require
    [clojure.string :as str]
    [play-clj.core :refer :all]
    [play-clj.g2d :refer :all]
    [zupa-wars.utils :refer :all]))

(defn c->img [c]
  (case c
    \f "forest.jpg"
    \m "mountain.jpg"
    \w "water.jpg"
    "plain.jpg"))

(defn- make-tile [row col c]
  (->
    (c->img c)
    (texture)
    (assoc :x (col->px col) :y (row->px row) :terrain c)))

(defn- make-row [row data]
  (map-indexed (partial make-tile row) data))

(defn terrain-load [name]
  (->>
    (slurp name)
    (str/split-lines)
    (reverse) ; hack
    (map-indexed make-row)))
