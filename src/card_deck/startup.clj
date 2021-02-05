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
    



(defn pickRandom [availableCards]
    (def countOfPickableCards 10);

    (def availableCardCounts (count availableCards));

    (def pickedCards (atom ()));

    (while (not= (count @pickedCards) countOfPickableCards )

        (def randomNumber (rand-int availableCardCounts));
        
        (def pickedCard (nth availableCards randomNumber));

        (def isCardAlreadyPicked (some (fn [x]
            (= x pickedCard);
            
            ) @pickedCards));

        (when-not (= isCardAlreadyPicked true )
            (swap! pickedCards conj pickedCard);  
        );
    );

    
    (def remainingCards  (second(diff (set @pickedCards) (set availableCards))));


    {:pickedCards @pickedCards, :remainingCards remainingCards};
)


(defn start []

    (def availableCards (atom cardDeck));

    ; (displayCards @availableCards);

    (println "Picked Cards")

    (println (pickRandom @availableCards))
    
)






