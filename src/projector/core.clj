(ns projector.core
  "Projector core with main funcionality"
  ^{:author "Santiago de Pedro"
   :added "1.0" }
  (:require [projector.device :refer [transmit closeable-device]]
            [projector.commands]))

(def ^:dynamic *device* nil)
(def commands-ns 'projector.commands)


(defn- load-commands
  "Load all commands from projector.commands and return a map"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  []
  (let [commands (keys (ns-publics commands-ns))
        commands (map #(hash-map (keyword %1) (var-get (ns-resolve commands-ns %1))) commands)]
    (into {} commands)))

(defmacro with-device
  "Use a device to perform commands with it"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [device & body]
  `(binding [*device* ~device]
     (with-open [device# (closeable-device *device*)]
       (do
         ~@body))))

(defn projector
  "Send command to projector using a device"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [command option]
  {:pre [(not (nil? *device*))]}
  (let [device *device*
        commands (load-commands)
        command-value (-> commands command option)]
    (transmit device command-value)))

(defn available-commands
  "Return a list with available commands"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  []
  (let [commands (load-commands)]
    (keys commands)))