(ns projector.core
  (:require [projector.device :refer [connect transmit closeable-device]]
            [projector.commands :refer :all])
  (:gen-class))

(def ^:dynamic *device* nil)
(def commands-ns 'projector.commands)


(defn- load-commands
  []
  (let [commands (keys (ns-publics commands-ns))
        commands (map #(hash-map (keyword %1) (eval %1)) commands)]
    (into {} commands)))

(defmacro with-device
  [device & body]
  `(binding [*device* ~device]
     (with-open [device# (closeable-device *device*)]
       (do
         (connect *device*)
         ~@body))))

(defn projector
  [command option]
  {:pre [(not (nil? *device*))]}
  (let [device *device*
        commands (load-commands)
        command-value (-> commands command option)]
    (transmit device command-value)))

