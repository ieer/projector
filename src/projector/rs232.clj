(ns projector.rs232
  (:require [serial.core :refer [open close! write]]
            [projector.device :refer [connect]])
  (:import (projector.device Device)))

(def ^:private dummy-port "dummy")
(def ^:dynamic ^:private *serial-port* nil)

(deftype RS232Projector [port])

(defn- if-not-dummy-port
  ([port not] (if-not-dummy-port not dummy-port))
  ([port not yes]
   #(if (not= port dummy-port) not yes)))

(defn- open-port
  [port]
  (if-not-dummy-port port (open port)))

(defn- send-command
  [port command]
  (do
    (if-not-dummy-port port (write port command))
    command))

(extend-type RS232Projector
  Device
  (connect [this] (set! *serial-port* (open-port (:port this))))
  (disconnect [this])
  (transmit [this command] (send-command *serial-port* command)))


(defn create-a-connection
  [port]
  (doto
    (RS232Projector. port)
    (connect)))