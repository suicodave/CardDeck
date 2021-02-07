(defproject card_deck "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
    [org.clojure/clojure "1.10.1"]
    [ultra-csv "0.2.3"]
    [mysql/mysql-connector-java "5.1.6"]
    [org.clojure/java.jdbc "0.7.12"]
  ]
  :repl-options {:init-ns card-deck.core}
  :main card_deck.core  
)
