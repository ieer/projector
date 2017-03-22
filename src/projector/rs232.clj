(ns projector.rs232
  (:require [serial.core :refer [open close! write]])
  (:import (projector.device Device)))

(deftype RS232Projector [port]
  Device
  (connect [this])
  (disconnect [this])
  (transmit [this command] (do (write port command) command)))


(defn create-a-connection
  [port]
  (let [serial-port (open port)]
    (RS232Projector. serial-port)))