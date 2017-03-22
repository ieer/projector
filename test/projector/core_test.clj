(ns projector.core-test
  (:require [clojure.test :refer :all]
            [projector.core :refer :all]
            [projector.commands :refer :all]
            [projector.rs232 :refer [create-a-connection]]))

(def rs232 (create-a-connection "dummy"))

(deftest commander
  (testing "Testing commands"
    (is (= (with-device rs232 (projector :power :on)) (:on power)))
    (is (= (with-device rs232 (projector :power :off)) (:off power)))
    (is (= (with-device rs232 (projector :hide :off)) (:off hide))))

  (testing "Available commands"
    (let [commands (available-commands)]
      (is (not-empty commands))
      (is (every? keyword? commands)))))
