(ns projector.core-test
  (:require [clojure.test :refer :all]
            [projector.core :refer :all]
            [projector.commands :refer :all]
            [projector.rs232 :refer [create-a-connection]]))

(def rs232 (create-a-connection "/dev/tty1"))

(deftest commander
  (testing "Testing commands"
    (is (= (with-device rs232 (projector :power :on)) (:on power)))
    (is (= (with-device rs232 (projector :power :off)) (:off power)))
    (is (= (with-device rs232 (projector :hide :off)) (:off hide)))))
