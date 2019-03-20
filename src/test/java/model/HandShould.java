package model;

import org.junit.Assert;
import org.junit.Test;
import utils.CardFactory;
import utils.HandFactory;

import java.util.List;

public class HandShould {

    @Test
    public void haveNoCardsByDefault() {
        Hand hand = new Hand();
        Assert.assertEquals(0, hand.getSize());
    }

    @Test
    public void beSortedBySuit() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createAceOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createAceOfSuit(Suit.DIAMONDS));
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createAceOfSuit(Suit.SPADES));
        hand.sortHandBySuit();
        List<Card> cards = hand.getCards();
        Assert.assertEquals(Suit.DIAMONDS, cards.get(0).getSuit());
        Assert.assertEquals(Suit.HEARTS, cards.get(1).getSuit());
        Assert.assertEquals(Suit.HEARTS, cards.get(2).getSuit());
        Assert.assertEquals(Suit.SPADES, cards.get(3).getSuit());
    }

    @Test
    public void beSortedByRankIfTheSuitIsTheSame() {
        Hand hand = new Hand();
        hand.addCard(CardFactory.createKingOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createQueenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createTenOfSuit(Suit.HEARTS));
        hand.addCard(CardFactory.createJackOfSuit(Suit.HEARTS));
        hand.sortHandBySuit();
        List<Card> cards = hand.getCards();
        Assert.assertEquals(Rank.TEN, cards.get(0).getRank());
        Assert.assertEquals(Rank.JACK, cards.get(1).getRank());
        Assert.assertEquals(Rank.QUEEN, cards.get(2).getRank());
        Assert.assertEquals(Rank.KING, cards.get(3).getRank());

    }

    @Test
    public void computeRoyalFlushRanking() {
        Hand hand = HandFactory.createRoyalFlushHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.ROYAL_FLUSH, handRanking);
    }

    @Test
    public void computeStraightFlushRanking() {
        Hand hand = HandFactory.createStraightFlushHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.STRAIGHT_FLUSH, handRanking);
    }

    @Test
    public void computeFourOfAKindRanking() {
        Hand hand = HandFactory.createFourOfAKindHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.FOUR_OF_A_KIND, handRanking);
    }

    @Test
    public void computeFullHouseRanking() {
        Hand hand = HandFactory.createFullHouseHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.FULL_HOUSE, handRanking);
    }

    @Test
    public void computeFlushRanking() {
        Hand hand = HandFactory.createFlushHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.FLUSH, handRanking);
    }

    @Test
    public void computeStraightRanking() {
        Hand hand = HandFactory.createStraightHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.STRAIGHT, handRanking);
    }

    @Test
    public void computeThreeOfAKindRanking() {
        Hand hand = HandFactory.createThreeOfAKindHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.THREE_OF_A_KIND, handRanking);
    }

    @Test
    public void haveHighCardRankingIfNoOtherRankingExists() {
        Hand hand = HandFactory.createHighCardHand();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.HIGH_CARD, handRanking);
    }
}
