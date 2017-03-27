(ns projector.core
  (:require [projector.device :refer [transmit closeable-device]]
            [projector.commands]))

(def ^:dynamic *device* nil)
(def commands-ns 'projector.commands)


(defn- load-commands
  []
  (let [commands (keys (ns-publics commands-ns))
        commands (map #(hash-map (keyword %1) (var-get (ns-resolve commands-ns %1))) commands)]
    (into {} commands)))

(defmacro with-device
  [device & body]
  `(binding [*device* ~device]
     (with-open [device# (closeable-device *device*)]
       (do
         ~@body))))

(defn projector
  [command option]
  {:pre [(not (nil? *device*)) (contains? (vector (keys (load-commands))) command)]}
  (let [device *device*
        commands (load-commands)
        command-value (-> commands command option)]
    (transmit device command-value)))

(defn available-commands
  []
  (let [commands (load-commands)]
    (keys commands)))