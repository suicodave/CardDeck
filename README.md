# Card Deck

# To run in Leiningen

Requires Leiningen installed to run.

type `lein run` at the root folder of this project.

# Note

CSV data are dumped in the `output` folder located at the root of the project.

For any issues, clarifications, or concern please feel free to write an `Issue`. Thank you!



# Database Setup

The app uses mysql as database, execute the database definition first to enable this feature. The script is found in `database\card_deck.sql`.

```clojure
(def dbConnection
  {
    :classname "com.mysql.jdbc.Driver"
    :subprotocol "mysql",
    :subname "//localhost:3306/card_deck"
    :user "root"
    :password "root"
  }  
)

```