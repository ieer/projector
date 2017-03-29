(ns projector.core-test
  (:require [clojure.test :refer :all]
            [projector.core :refer :all]
            [projector.commands :refer :all]
            [projector.rs232 :refer [create-a-connection]]))

(def rs232 (create-a-connection "dummy"))

(deftest commander
  (testing "Testing commands"
    (with-device rs232
                 (is (= (projector :power :on) (:on power)))
                 (is (= (projector :power :off) (:off power)))
                 (is (= (projector :hide :off) (:off hide)))))

  (testing "Available commands"
    (let [commands (available-commands)]
      (is (not-empty commands))
      (is (every? keyword? commands)))))
