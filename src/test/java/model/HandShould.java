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
        Assert.assertEquals(Rank.KING, cards.get(0).getRank());
        Assert.assertEquals(Rank.QUEEN, cards.get(1).getRank());
        Assert.assertEquals(Rank.JACK, cards.get(2).getRank());
        Assert.assertEquals(Rank.TEN, cards.get(3).getRank());

    }

    @Test
    public void computeRoyalFlushRanking() {
        Hand hand = HandFactory.createRoyalFlushHand();
        hand.rankHand();
        HandRanking handRanking = hand.getHandRanking();
        Assert.assertEquals(HandRankingCategory.ROYAL_FLUSH, handRanking.getHandRankingCategory());
    }

    @Test
    public void computeStraightFlushRankingWithCorrectHighCardAndKicker() {
        Hand hand = HandFactory.createStraightFlushHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.STRAIGHT_FLUSH, Rank.KING, Rank.EIGHT);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void computeFourOfAKindRankingWithCorrectHighCardAndKicker() {
        Hand hand = HandFactory.createFourOfAKindHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.KING, Rank.NINE);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void computeFullHouseRankingWithCorrectTripleAndPairCards() {
        Hand hand = HandFactory.createFullHouseHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.FULL_HOUSE, Rank.KING, Rank.QUEEN);
        Assert.assertEquals(actualRanking, expectedRanking);
    }

    @Test
    public void computeFlushRankingWithCorrectHighCardAndKicker() {
        Hand hand = HandFactory.createFlushHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.FLUSH, Rank.KING, Rank.QUEEN);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void computeStraightRankingWithCorrectHighCardAndKicker() {
        Hand hand = HandFactory.createStraightHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.STRAIGHT, Rank.KING, Rank.KING);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void computeThreeOfAKindRankingWithCorrectHighCardAndKicker() {
        Hand hand = HandFactory.createThreeOfAKindHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.THREE_OF_A_KIND, Rank.KING, Rank.QUEEN);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void computeTwoPairRankingWithCorrectHighCards() {
        Hand hand = HandFactory.createTwoPairHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.TWO_PAIR, Rank.KING, Rank.QUEEN);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void computeOnePairRankingWithCorrectHighCardAndKicker() {
        Hand hand = HandFactory.createOnePairHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.ONE_PAIR, Rank.KING, Rank.QUEEN);
        Assert.assertEquals(expectedRanking, actualRanking);
    }

    @Test
    public void haveHighCardRankingIfNoOtherRankingExists() {
        Hand hand = HandFactory.createHighCardHand();
        hand.rankHand();
        HandRanking actualRanking = hand.getHandRanking();
        HandRanking expectedRanking = new HandRanking(HandRankingCategory.HIGH_CARD, Rank.KING);
        Assert.assertEquals(expectedRanking, actualRanking);
    }
}
