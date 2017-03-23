(defproject projector "0.7.0"
  :description "Control Dell 4220 Projector through clojure"
  :url "https://github.com/ieer/projector"
  :license {:name "GNU General Public License v3.0"
            :url  "https://github.com/ieer/projector/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-serial "2.0.3"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/CLOJAR_USERNAME
                              :password      :env/CLOJAR_PASSWORD
                              :sign-releases false}]])
