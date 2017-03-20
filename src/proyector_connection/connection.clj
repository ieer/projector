(ns proyector-connection.connection)


(defprotocol Connection
  (connect [this])
  (disconnect [this])
  (transmit [this command]))