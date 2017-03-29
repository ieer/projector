(ns projector.rs232
  "RS232 serial connections"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  (:require [serial.core :refer [open close! write]])
  (:import (projector.device Device)))

(def ^:private dummy-port "dummy")
(def ^:private dummy-port? #(= % dummy-port))
(def ^:private not-dummy-port? #(not (dummy-port? %)))


(defmacro if-not-dummy-port
  "Perform an action if port is not dummy, otherwise return dummy"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [port not]
  `(if (not-dummy-port? ~port) ~not dummy-port))

(defn- open-port
  "Open a rs232 port"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [port]
  (if-not-dummy-port port (open port)))

(defn- close-port
  "Close a rs232 port"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [port & [time-to-wait]]
  (if-not-dummy-port port (future
                            (Thread/sleep (* 1000 (or time-to-wait 1)))
                            (close! port)
                            true)))

(defn- send-command
  "Send a command via rs232 port"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [port command]
  (do
    (if-not-dummy-port port (write port command))
    command))

(deftype RS232Projector [port]
  "RS232 projector device "
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  Device
  (disconnect [this] (close-port port 2))
  (transmit [this command] (send-command port command)))


(defn create-a-connection
  "Create a rs232 projector device"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [port]
  (let [serial-port (open-port port)]
    (RS232Projector. serial-port)))