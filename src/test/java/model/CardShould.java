package model;

import org.junit.Assert;
import org.junit.Test;

public class CardShould {

    @Test
    public void beCreatedWithASuitAndValue() {
        Card card = new Card(Suit.HEARTS, 13);
        Suit suit = card.getSuit();
        int value = card.getValue();
        Assert.assertEquals(Suit.HEARTS, suit);
        Assert.assertEquals(13, value);
    }
}
