(ns
  ^{:doc    "RS232 projector serial connections"
    :author "Santiago de Pedro"
    :added  "1.0"}
  projector.rs232
  (:require [serial.core :refer [open close! write]]
            [projector.device :refer [disconnect]])
  (:import (projector.device Device)
           (java.io Closeable)))

(def ^:private dummy-port "dummy")
(def ^:private dummy-port? #(= % dummy-port))
(def ^:private not-dummy-port? #(not (dummy-port? %)))


(defmacro if-not-dummy-port
  "Perform an action if port is not dummy, otherwise return dummy"
  ^{:author "Santiago de Pedro"
    :added  "1.0"}
  [port not]
  `(if (not-dummy-port? ~port) ~not dummy-port))

(defn- open-port
  "Open a rs232 port"
  ^{:author "Santiago de Pedro"
    :added  "1.0"}
  [port]
  (if-not-dummy-port port (open port)))

(defn- close-port
  "Close a rs232 port"
  ^{:author "Santiago de Pedro"
    :added  "1.0"}
  [port & [time-to-wait]]
  (if-not-dummy-port port (future
                            (Thread/sleep (* 1000 (or time-to-wait 1)))
                            (close! port)
                            true)))

(defn- send-command
  "Send a command via rs232 port"
  ^{:author "Santiago de Pedro"
    :added  "1.0"}
  [port command]
  (do
    (if-not-dummy-port port (write port command))
    command))

; RS232 projector device
; Implements Device protocol and Closeable interface
(deftype RS232Projector [port]
  Device
  (disconnect [this] (close-port port 2))
  (transmit [this command] (send-command port command))
  Closeable
  (close [this] (disconnect this)))


(defn create-a-connection
  "Create a rs232 projector device"
  ^{:author "Santiago de Pedro"
    :added  "1.0"}
  [port]
  (let [serial-port (open-port port)]
    (RS232Projector. serial-port)))