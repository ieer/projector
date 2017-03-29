(ns projector.rs232
  (:require [serial.core :refer [open close! write]])
  (:import (projector.device Device)))

(def ^:private dummy-port "dummy")
(def ^:private dummy-port? #(= % dummy-port))
(def ^:private not-dummy-port? #(not (dummy-port? %)))


(defmacro if-not-dummy-port
  [port not]
  `(if (not-dummy-port? ~port) ~not dummy-port))

(defn- open-port
  [port]
  (if-not-dummy-port port (open port)))

(defn- close-port
  [port & [time-to-wait]]
  (if-not-dummy-port port (future
                            (Thread/sleep (* 1000 (or time-to-wait 1)))
                            (close! port)
                            true)))

(defn- send-command
  [port command]
  (do
    (if-not-dummy-port port (write port command))
    command))

(deftype RS232Projector [port]
  Device
  (disconnect [this] (close-port port 2))
  (transmit [this command] (send-command port command)))


(defn create-a-connection
  [port]
  (let [serial-port (open-port port)]
    (RS232Projector. serial-port)))