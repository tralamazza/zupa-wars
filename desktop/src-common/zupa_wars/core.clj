(ns zupa-wars.core
  (:require
    [play-clj.core :refer :all]
    [play-clj.g2d :refer :all]
    [play-clj.repl :as repl]
    [zupa-wars.terrain :refer :all]
    [zupa-wars.utils :refer :all]))

(declare zupa-wars main-screen)

(defn- make-unit [col row unit]
  (assoc (texture unit) :x (col->px col) :y (row->px row)))

(defn- move-entity [direction shift entity]
  (case direction
    :up (assoc entity :y (+ (:y entity) (row->px shift)))
    :down (assoc entity :y (- (:y entity) (row->px shift)))
    :right (assoc entity :x (+ (:x entity) (col->px shift)))      
    :left (assoc entity :x (- (:x entity) (col->px shift)))))

(defn- make-cursor [col row]
  (->
    (texture "cursor.png")
    (assoc :x (col->px col) :y (row->px row) :cursor? true)))

(defn- move-cursor [direction entities]
  (map
    (fn [entity]
        (if (:cursor? entity)
          (move-entity direction 1 entity)
          entity))
    entities))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage) :camera (orthographic))
    [(terrain-load "resources/terrain.txt")
     (make-unit 5 5 "bomber.png")
     (make-unit 8 4 "heli.png")
     (make-unit 2 7 "tank.png")
     (make-cursor 10 5)])

  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities))

  :on-resize
  (fn [screen entities]
    (width! screen 320))
 
  :on-key-down
  (fn [screen entities]
    (condp = (:key screen)
      (key-code :q) (app! :exit)
      (key-code :r) (app! :post-runnable #(set-screen! zupa-wars main-screen))
      (key-code :dpad-left) (move-cursor :left entities)
      (key-code :dpad-right) (move-cursor :right entities)
      (key-code :dpad-up) (move-cursor :up entities)
      (key-code :dpad-down) (move-cursor :down entities)
      entities)))

(defgame zupa-wars
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
