package model;

import java.util.Comparator;

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String toString() {
        return rank.getSymbol() + " of " + suit.toString();
    }

    public static final Comparator<Card> suitOrder = (Card card1, Card card2) -> {
        int compareSuits = Integer.compare(card1.suit.ordinal(), card2.suit.ordinal());
        if (compareSuits == 0) {
            return Integer.compare(card1.rank.ordinal(), card2.rank.ordinal());
        } else {
            return compareSuits;
        }
    };

    public static final Comparator<Card> rankOrder = (Card card1, Card card2) -> {
        int compareRanks = Integer.compare(card1.rank.ordinal(), card2.rank.ordinal());
        if (compareRanks == 0) {
            return Integer.compare(card1.suit.ordinal(), card2.suit.ordinal());
        } else {
            return compareRanks;
        }
    };

}
