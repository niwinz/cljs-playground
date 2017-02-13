(ns testproject.core
  (:require [beicon.extern.rxjs]))

(enable-console-print!)

(defn main
  [& args]
  (println "Hello world"))

(set! *main-cli-fn* main)
