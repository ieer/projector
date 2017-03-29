(ns projector.device
  (:import (java.io Closeable)))


(defprotocol Device
  (disconnect [this])
  (transmit [this command]))

(defn closeable-device [device]
  (reify
    Closeable
    (close [this] (disconnect device))))
