(ns card_deck.startup
    (:require [card_deck.repositories.card_repository :as card])
    (:use clojure.data)
    )


(def cardDeck (card/createCardDeck))


(defn displayCards [availableCards]

    (def cardCount (count availableCards))

    (dotimes [n cardCount]
        (def cardName (nth availableCards n))
        
        (println cardName)

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


    {:pickedCards @pickedCards, :remainingCards (into () remainingCards)};
)





(defn start []

    (def availableCards (atom cardDeck));
    

    (println "Welcome Guest!");
    (println "---------------------")
    (println "Here are your deck of cards:")
    (println "---------------------")

    (displayCards @availableCards);

    (println "---------------------")
    (println "Press any key to pick 10 random cards");
    (println "---------------------")

    
    (println "Picked Cards: ")
    (println "---------------------")

    (def result (pickRandom @availableCards));


    (def pickedCards (:pickedCards result));

    (def remainingCards (:remainingCards result));

    (displayCards pickedCards)
    (println "---------------------");


    (println "Remaining Cards: ")
    (println "---------------------")


    (displayCards remainingCards);
    (println "---------------------");


    (println "Press any key to save in csv");
    (println "---------------------");

    (println "Saving picked cards...");
    (println "---------------------");

    ;;Save picked cards here

    (println "Picked cards successfully saved");
    (println "---------------------");


    (println "Saving remaining cards...");
    (println "---------------------");

    ;;Save remaining cards here

    (println "Remaining cards successfully saved");
    (println "---------------------");
)






