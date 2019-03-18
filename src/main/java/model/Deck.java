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
            for (Rank rank: Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        return cards;
    }

    public int getSize() {
        return cards.size();
    }
}
