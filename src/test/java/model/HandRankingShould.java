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
}
