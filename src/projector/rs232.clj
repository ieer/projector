(ns projector.rs232
  ;(:require [serial.core :refer [open close! write]])
  (:import (projector.device Device)))

(def dummy-port "dummy")

(deftype RS232Projector [port]
  Device
  (connect [this])
  (disconnect [this])
  (transmit [this command] (if (not= port dummy-port)
                             (do command command)
                             command)))
;[clj-serial "2.0.4-SNAPSHOT"]
;(write port command)
;(open port)
(defn create-a-connection
  [port]
  (let [serial-port (if (not= port dummy-port) port dummy-port)]
    (RS232Projector. serial-port)))