(ns card_deck.repositories.card_repository
  (:require [clojure.java.io :as io])
  (:use clojure.data)
  (:require [clojure.string :as str])
  (:require [ultra-csv.core :as csvReader])
  (:require [clojure.java.jdbc :as sql])
  );


(def dbConnection
  {
    :classname "com.mysql.jdbc.Driver"
    :subprotocol "mysql",
    :subname "//localhost:3306/card_deck"
    :user "root"
    :password "root"
  }  
)



(defn createCardName [category number]

  (def cardNumericValue 
    (case number
      11 "Jack"  
      12 "Queen"
      13 "King"
      number
    )  
    
  )

  (apply str cardNumericValue " of " category)
);


(def spades "Spades");

(def hearts "Hearts");

(def clubs "Clubs");

(def diamonds "Diamonds");

(def spadesShortName "SPD")

(def heartsShortName "HRT")

(def clubsShortName "CLB")

(def diamondsShortName "DMD")

(def cardCategories [diamonds clubs hearts spades]);

(def longNameMapping 
  (hash-map
    (keyword spades) spadesShortName,
    (keyword hearts) heartsShortName,
    (keyword clubs) clubsShortName,
    (keyword diamonds) diamondsShortName
    )
)

(def shortNameMapping
  (hash-map
    (keyword spadesShortName) spades,
    (keyword heartsShortName) hearts,
    (keyword clubsShortName) clubs,
    (keyword diamondsShortName) diamonds
    )
)


(defn createCardSet [category]

  (def cardSet (atom []))

  (def nCardsPerCategory 13)

  (dotimes [n nCardsPerCategory]

    (def cardName (
      createCardName category (inc n))
      )

    (swap! cardSet conj cardName)
  )

  @cardSet
    
);

(defn createCardDeck []
  
  (def iterations  (count cardCategories))

  (def cardDeck (atom []))
  
  (dotimes [n iterations]
    
    (def category (get cardCategories n))

    (def cardSet (createCardSet category))

    (swap! cardDeck conj cardSet)
    
  )

  (flatten @cardDeck)
);

(defn toShortName [card]
  (def name (str/split card #" "))
  (def category (last name))
  (def shortName ((keyword category) longNameMapping))
  (def poppedName (pop name))
  (def vectorizedShortName (conj poppedName shortName))
  (str/join " " vectorizedShortName)
)


(defn toLongName [card]
  (def name (str/split card #" "))
  (def category (last name))
  (def shortName ((keyword category) shortNameMapping))
  (def poppedName (pop name))
  (def vectorizedShortName (conj poppedName shortName))
  (str/join " " vectorizedShortName)
)


(defn exportCsv [items filename]
  (with-open [writer (io/writer filename)]
    (doseq [i items]
      (def shortenedName (toShortName i))
      (.write writer (str  shortenedName  "\n"))  
    )  
  )
  
)

(defn isCardAlreadyPicked [card availableCards]
  (some (fn [x]
     (= x card);
     
     ) availableCards);
);


(defn pickRandom [availableCards]
  (def countOfPickableCards 10);

  (def availableCardCounts (count availableCards));

  (def pickedCards (atom ()));

  (while (not= (count @pickedCards) countOfPickableCards )

      (def randomNumber (rand-int availableCardCounts));
      
      (def pickedCard (nth availableCards randomNumber));

      (def isCardTaken (isCardAlreadyPicked pickedCard @pickedCards));


      (when-not (true? isCardTaken )
          (swap! pickedCards conj pickedCard);  
      );
  );

  
  (def remainingCards  (second(diff (set @pickedCards) (set availableCards))));

  (def sortedRemainingCards (sort (into () remainingCards)))

  (def sortedPickedCards (sort @pickedCards))

  {:pickedCards sortedPickedCards, :remainingCards sortedRemainingCards};
)


(defn printFromCSV [filename]

  (def fileExists(.exists (io/file filename)))

  (when (true? fileExists)
    (def items (csvReader/read-csv filename))

    (doseq [item items]
      (def card (str/join " " item))
      (println   (toLongName card))  
    )
  )

  (when-not (true? fileExists)
    (println "No saved data from the previous session.")
  )

)


(defn saveToDatabase [cards table]

  (
    sql/execute! dbConnection [(str "truncate table " table)]
  )
  
  (def names 
    (for [x cards] {:name (toShortName x)})  
  )

  (
      sql/insert-multi! dbConnection (keyword table) names 
  )
  
)

(defn savePickedCardsToDB [cards]
  (saveToDatabase cards "picked_cards")  
)

(defn saveRemainingCardsToDB [cards]

  (saveToDatabase cards "remaining_cards")  
)