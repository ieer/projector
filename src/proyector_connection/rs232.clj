(ns proyector-connection.rs232
  (:import (proyector_connection.connection Connection)))


(deftype RS232 [port]
  Connection
  (connect [this] port)
  (disconnect [this] "OK")
  (transmit [this command] command))