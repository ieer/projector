(ns projector.device
  "Device namespace that contains common protocols and functions"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  (:import (java.io Closeable)))


(defprotocol Device
  "Device protocol with common device functions"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  (disconnect [this] "Disconnect device")
  (transmit [this command] "Send command to device"))

(defn closeable-device
  "Make a closeable device implementing Closeable interface"
  ^{:author "Santiago de Pedro"
    :added "1.0" }
  [device]
  (reify
    Closeable
    (close [this] (disconnect device))))
