(ns projector.core-test
  (:require [clojure.test :refer :all]
            [projector.core :refer :all]
            [projector.commands :refer :all]
            [projector.rs232 :refer [->RS232]]))

(def rs232 (->RS232 "/dev/ttyUSB0"))

(deftest commander
  (testing "Testing commands"
    (is (= (with-device rs232 (projector :power :on)) (:on power)))
    (is (= (with-device rs232 (projector :power :off)) (:off power)))
    (is (= (with-device rs232 (projector :hide :off)) (:off hide)))))
