package model;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = initialiseStartingDeck();
    }

    private List<Card> initialiseStartingDeck() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit: Suit.values()) {
            for (int val = 2; val <= 14; val++) {
                cards.add(new Card(suit, val));
            }
        }
        return cards;
    }

    public int getSize() {
        return cards.size();
    }
}
