(defproject err "0.1.4"
  :description "Error handling based on cats library"
  :url "https://github.com/s-mage/err"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [funcool/cats "2.3.2"]]
  :profiles {:dev {:repl-options {:init-ns err.core}}
             :uberjar {:aot :all}})
