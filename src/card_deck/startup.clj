(ns card_deck.startup
    (:require [card_deck.repositories.card_repository :as card])
    (:require [ultra-csv.core :as csvReader])
    )


(def cardDeck (card/createCardDeck))


(defn displayCards [availableCards]

    (def cardCount (count availableCards))

    (dotimes [n cardCount]
        (def cardName (nth availableCards n))
        
        (println cardName)

    )
)
    


(defn start []

    (def availableCards (atom cardDeck));

    (println "Welcome Guest!");
    (println "---------------------");


    (println "Reading saved picked cards:");
    (println "---------------------");
    (card/printFromCSV "output/pickCards.csv");
    (println "---------------------");


    (println "Reading saved remaining cards:");
    (println "---------------------");
    (card/printFromCSV "output/remainingCards.csv");
    (println "---------------------");


    (println "Here are your deck of cards:")
    (println "---------------------")

    (displayCards @availableCards);

    (println "---------------------")
    (println "Picking 10 random cards...");
    (println "---------------------")

    
    (println "Picked Cards: ")
    (println "---------------------")

    (def result (card/pickRandom @availableCards));


    (def pickedCards (:pickedCards result));

    (def remainingCards (:remainingCards result));


    (card/savePickedCardsToDB pickedCards )

    (card/saveRemainingCardsToDB remainingCards )

    (displayCards pickedCards)
    (println "---------------------");


    (println "Remaining Cards: ")
    (println "---------------------")


    (displayCards remainingCards);
    (println "---------------------");


    (println "Saving picked cards...");
    (println "---------------------");

    (card/exportCsv pickedCards "output/pickCards.csv")

    (println "Picked cards successfully saved");
    (println "---------------------");


    (println "Saving remaining cards...");
    (println "---------------------");

    ;;Save remaining cards here
    (card/exportCsv remainingCards "output/remainingCards.csv")

    (println "Remaining cards successfully saved");
    (println "---------------------");

    
)






