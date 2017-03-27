(ns projector.rs232
  (:require [serial.core :refer [open close! write]]
            [projector.device :refer [connect]])
  (:import (projector.device Device)))

(def dummy-port "dummy")

(deftype RS232Projector [^:volatile-mutable port]
  Device
  (connect [this] (do (set! port
                            (if (= port dummy-port)
                              dummy-port
                              (open port)))
                      this))
  (disconnect [this])
  (transmit [this command] (if (not= port dummy-port)
                             (do (write port command) command)
                             command)))

(defn create-a-connection [port] (-> port RS232Projector. connect))