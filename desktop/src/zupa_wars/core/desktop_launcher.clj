(ns zupa-wars.core.desktop-launcher
  (:require [zupa-wars.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. zupa-wars "zupa-wars" 640 480)
  (Keyboard/enableRepeatEvents true))
