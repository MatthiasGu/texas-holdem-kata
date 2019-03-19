package utils;

import model.Card;
import model.Rank;
import model.Suit;

public class CardFactory {
    public CardFactory() {

    }

    public static Card createAceOfSuit(Suit suit) {
        return new Card(suit, Rank.ACE);
    }

    public static Card createKingOfSuit(Suit suit) {
        return new Card(suit, Rank.KING);
    }

    public static Card createQueenOfSuit(Suit suit) {
        return new Card(suit, Rank.QUEEN);
    }

    public static Card createJackOfSuit(Suit suit) {
        return new Card(suit, Rank.JACK);
    }

    public static Card createTenOfSuit(Suit suit) {
        return new Card(suit, Rank.TEN);
    }

    public static Card createNineOfSuit(Suit suit) {
        return new Card(suit, Rank.NINE);
    }

    public static Card createEightOfSuit(Suit suit) {
        return new Card(suit, Rank.EIGHT);
    }

    public static Card createSevenOfSuit(Suit suit) {
        return new Card(suit, Rank.SEVEN);
    }

    public static Card createSixOfSuit(Suit suit) {
        return new Card(suit, Rank.SIX);
    }

    public static Card createFiveOfSuit(Suit suit) {
        return new Card(suit, Rank.FIVE);
    }

    public static Card createFourOfSuit(Suit suit) {
        return new Card(suit, Rank.FOUR);
    }

    public static Card createThreeOfSuit(Suit suit) {
        return new Card(suit, Rank.TREY);
    }

    public static Card createTwoOfSuit(Suit suit) {
        return new Card(suit, Rank.DEUCE);
    }
}
