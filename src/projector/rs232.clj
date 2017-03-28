(ns projector.rs232
  (:require [serial.core :refer [open close! write]]
            [projector.device :refer [connect]])
  (:import (projector.device Device)))

(def ^:private dummy-port "dummy")
(def ^:dynamic ^:private *serial-port* nil)


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

(deftype RS232Projector [port]
  Device
  (connect [this] (set! *serial-port* (open-port port)))
  (disconnect [this])
  (transmit [this command] (send-command *serial-port* command)))


(defn create-a-connection
  [port]
  (doto
    (RS232Projector. port)
    (connect)))