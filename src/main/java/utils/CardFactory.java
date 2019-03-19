package utils;

import model.Card;
import model.Rank;
import model.Suit;

public class CardFactory {
    public CardFactory() {

    }

    public static Card createAceOfHearts() {
        return new Card(Suit.HEARTS, Rank.ACE);
    }

    public static Card createKingOfHearts() {
        return new Card(Suit.HEARTS, Rank.KING);
    }

    public static Card createQueenOfHearts() {
        return new Card(Suit.HEARTS, Rank.QUEEN);
    }

    public static Card createJackOfHearts() {
        return new Card(Suit.HEARTS, Rank.JACK);
    }

    public static Card createTenOfHearts() {
        return new Card(Suit.HEARTS, Rank.TEN);
    }

    public static Card createNineOfHearts() {
        return new Card(Suit.HEARTS, Rank.NINE);
    }

    public static Card createEightOfHearts() {
        return new Card(Suit.HEARTS, Rank.EIGHT);
    }

    public static Card createAceOfSpades() {
        return new Card(Suit.SPADES, Rank.ACE);
    }

    public static Card createAceOfDiamonds() {
        return new Card(Suit.DIAMONDS, Rank.ACE);
    }
}
