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
        hand.sortHand();
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
        hand.sortHand();
        List<Card> cards = hand.getCards();
        Assert.assertEquals(Rank.TEN, cards.get(0).getRank());
        Assert.assertEquals(Rank.JACK, cards.get(1).getRank());
        Assert.assertEquals(Rank.QUEEN, cards.get(2).getRank());
        Assert.assertEquals(Rank.KING, cards.get(3).getRank());

    }

    @Test
    public void computeRoyalFlushRanking() {
        Hand hand = HandFactory.createRoyalFlush();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.ROYAL_FLUSH, handRanking);
    }

    @Test
    public void computeStraightFlushRanking() {
        Hand hand = HandFactory.createStraightFlush();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.STRAIGHT_FLUSH, handRanking);
    }

    @Test
    public void haveHighCardRankingIfNoOtherRankingExists() {
        Hand hand = HandFactory.createHighCard();
        HandRanking handRanking = hand.getRanking();
        Assert.assertEquals(HandRanking.HIGH_CARD, handRanking);
    }
}
