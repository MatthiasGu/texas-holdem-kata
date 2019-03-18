package model;

import org.junit.Assert;
import org.junit.Test;

public class CardShould {

    @Test
    public void beCreatedWithASuitAndValue() {
        Card card = new Card(Suit.HEARTS, Rank.KING);
        Suit suit = card.getSuit();
        Rank rank = card.getRank();
        Assert.assertEquals(Suit.HEARTS, suit);
        Assert.assertEquals(Rank.KING, rank);
    }
}
