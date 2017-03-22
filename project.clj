(defproject projector "0.1.0-SNAPSHOT"
  :description "Control Dell 4220 Projector through a connection"
  :url "https://github.com/ieer/projector"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot projector.core
  :javac-options ["-target" "1.8"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
