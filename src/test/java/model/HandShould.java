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
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.ROYAL_FLUSH, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeStraightFlushRanking() {
        Hand hand = HandFactory.createStraightFlushHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.STRAIGHT_FLUSH, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeFourOfAKindRanking() {
        Hand hand = HandFactory.createFourOfAKindHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.FOUR_OF_A_KIND, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeFullHouseRanking() {
        Hand hand = HandFactory.createFullHouseHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.FULL_HOUSE, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeFlushRanking() {
        Hand hand = HandFactory.createFlushHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.FLUSH, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeStraightRanking() {
        Hand hand = HandFactory.createStraightHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.STRAIGHT, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeThreeOfAKindRanking() {
        Hand hand = HandFactory.createThreeOfAKindHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.THREE_OF_A_KIND, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeTwoPairRanking() {
        Hand hand = HandFactory.createTwoPairHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.TWO_PAIR, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeOnePairRanking() {
        Hand hand = HandFactory.createOnePairHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.ONE_PAIR, handRanking.getHandRankingCategory());
    }

    @Test
    public void haveHighCardRankingIfNoOtherRankingExists() {
        Hand hand = HandFactory.createHighCardHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.HIGH_CARD, handRanking.getHandRankingCategory());
    }
}
