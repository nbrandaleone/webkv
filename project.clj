(defproject webkv "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
;; GraalVM and Clojure do not always work well. 
;; Version 1.9.0 works better than latest Clojure builds.
  ;;:dependencies [[org.clojure/clojure "1.10.1"]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [http-kit "2.3.0"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-defaults "0.3.2"]
                 [bidi "2.1.6"]]
  :repl-options {:init-ns webkv.core}
  :main webkv.core
  :aot [webkv.core])
