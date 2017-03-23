(defproject projector "0.4.0"
  :description "Control Dell 4220 Projector through clojure"
  :url "https://github.com/ieer/projector"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-serial "2.0.4-SNAPSHOT"]]
  :main ^:skip-aot projector.core
  :javac-options ["-target" "1.8"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repositories [["releases" {:url "https://clojars.org/repo"
                              :username :env/CLOJAR_USERNAME
                              :password :env/CLOJAR_PASSWORD}]])
