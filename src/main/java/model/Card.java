package model;

public class Card implements Comparable<Card>{

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

    @Override
    public int compareTo(Card card) {
        int compareSuits = Integer.compare(this.suit.ordinal(), card.suit.ordinal());
        if (compareSuits == 0) {
            return Integer.compare(this.rank.ordinal(), card.rank.ordinal());
        } else {
            return compareSuits;
        }
    }
}
