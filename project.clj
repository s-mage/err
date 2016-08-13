(defproject err "0.1.3"
  :description "Error handling based on cats library"
  :url "https://github.com/s-mage/err"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [funcool/cats "2.0.0"]]
  :profiles {:dev {:repl-options {:init-ns err.core}}
             :uberjar {:aot :all}})
