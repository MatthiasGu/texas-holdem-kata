package model;

import org.junit.Assert;
import org.junit.Test;

public class HandRankingShould {

    @Test
    public void beCorrectBasedOnRankingCategory() {
        HandRanking handRanking1 = new HandRanking(HandRankingCategory.ROYAL_FLUSH);
        HandRanking handRanking2 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND);
        Assert.assertEquals(1, handRanking1.compareTo(handRanking2));
    }

    @Test
    public void returnZeroInCaseOfTwoRoyalFlushes() {
        HandRanking handRanking1 = new HandRanking(HandRankingCategory.ROYAL_FLUSH);
        HandRanking handRanking2 = new HandRanking(HandRankingCategory.ROYAL_FLUSH);
        Assert.assertEquals(0, handRanking1.compareTo(handRanking2));
    }

    @Test
    public void beCorrectBasedOnFirstTiebreak() {
        HandRanking handRanking1 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.ACE);
        HandRanking handRanking2 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.KING);
        Assert.assertEquals(1, handRanking1.compareTo(handRanking2));
    }

    @Test
    public void returnZeroIfFirstTiebreakIsTheSameForBothHands() {
        HandRanking handRanking1 = new HandRanking(HandRankingCategory.HIGH_CARD, Rank.KING);
        HandRanking handRanking2 = new HandRanking(HandRankingCategory.HIGH_CARD, Rank.KING);
        Assert.assertEquals(0, handRanking1.compareTo(handRanking2));
    }

    @Test
    public void beCorrectBasedOnSecondTiebreak() {
        HandRanking handRanking1 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.ACE, Rank.KING);
        HandRanking handRanking2 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.ACE, Rank.QUEEN);
        Assert.assertEquals(1, handRanking1.compareTo(handRanking2));
    }

    @Test
    public void returnZeroIfSecondTiebreakIsTheSameForBothHands() {
        HandRanking handRanking1 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.ACE, Rank.KING);
        HandRanking handRanking2 = new HandRanking(HandRankingCategory.FOUR_OF_A_KIND, Rank.ACE, Rank.KING);
        Assert.assertEquals(0, handRanking1.compareTo(handRanking2));
    }
}
