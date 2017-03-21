(ns projector.rs232
  (:import (projector.device Device)))

(deftype RS232 [port]
  Device
  (connect [this] port)
  (disconnect [this] port)
  (transmit [this command] command))