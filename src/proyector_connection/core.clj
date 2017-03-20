(ns proyector-connection.core
  (:require [proyector-connection.connection :only [connect disconnect transmit]])
  (:gen-class))

(def ^:dynamic *connection* nil)


(defn- load-commands
  []
  (let [commands (keys (ns-publics 'proyector-rs232.commands))
        commands (map #(hash-map (keyword %1) (eval %1)) commands)]
    (into {} commands)))

(defmacro with-connection
  [connection & body]
  `(binding
     [*connection* ~connection]
     (do
       (connect *connection*)
       ~@body
       (disconnect *connection*))))

(defn proyector
  [command option]
  {:pre [(not (nil? *connection*))]}
  (let [connection *connection*
        commands (load-commands)
        command-value (-> commands command option)]
    (transmit connection command-value)))

