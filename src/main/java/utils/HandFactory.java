package utils;

import model.Hand;
import model.Suit;

public class HandFactory {
    public HandFactory() {

    }

    /**
     * Creates a {@link Hand} containing a Royal Flush (10, J, Q, K, A of the same suit).
     */
    public static Hand createRoyalFlushHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createAceOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createTenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing a Straight Flush (5 consecutive cards of the same suit, e.g. Q, K, A, 2, 3).
     */
    public static Hand createStraightFlushHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createTenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing Four of a Kind (Four cards of the same rank).
     */
    public static Hand createFourOfAKindHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.SPADES));
        hand.addCard(CardFactory.createKingOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing a Full House(Three of a Kind + Pair).
     */
    public static Hand createFullHouseHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.SPADES));
        hand.addCard(CardFactory.createKingOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing a Flush (5 cards of the same suit).
     */
    public static Hand createFlushHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createTenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing a Straight (5 consecutive cards of any suit, e.g. Q, K, A, 2, 3).
     */
    public static Hand createStraightHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createTenOfSuit(Suit.SPADES));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createFourOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing Three of a Kind (Three cards of the same rank).
     */
    public static Hand createThreeOfAKindHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.SPADES));
        hand.addCard(CardFactory.createKingOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing Two Pair (Two lots of two cards of the same rank).
     */
    public static Hand createTwoPairHand() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.SPADES));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.CLUBS));
        hand.addCard(CardFactory.createNineOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createEightOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createSevenOfSuit(Suit.HEARTS));
        return hand;
    }

    /**
     * Creates a {@link Hand} containing no card combinations that can produce a ranking.
     */
    public static Hand createHighCardHand() {
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
