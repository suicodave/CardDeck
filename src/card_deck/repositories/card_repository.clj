(ns card_deck.repositories.card_repository);

(defn createCardName [category number]

  (apply str number " of " category));


(def spades "Spades");

(def hearts "Hearts");

(def clubs "Clubs");

(def diamonds "Diamonds");

(def cardCategories [diamonds clubs hearts spades]);


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