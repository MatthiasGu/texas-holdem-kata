package utils;

import model.Hand;
import model.Suit;

public class HandFactory {
    public HandFactory() {

    }
    public static Hand createRoyalFlush() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createAceOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createTenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.HEARTS));
        return hand;
    }

    public static Hand createHighCard() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.SPADES));
        hand.addCard(CardFactory.createTenOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createFiveOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createFourOfSuit(Suit.SPADES));
        return hand;
    }
}
