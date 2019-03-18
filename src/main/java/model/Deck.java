package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Card drawCard() {
        Random randomGenerator = new Random();
        int randomCardIndex = randomGenerator.nextInt(51);
        return cards.remove(randomCardIndex);
    }

    public int getSize() {
        return cards.size();
    }
}
