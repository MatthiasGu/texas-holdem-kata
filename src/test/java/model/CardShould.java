package model;

import org.junit.Assert;
import org.junit.Test;

public class CardShould {

    @Test
    public void beCreatedWithAValueAndSuit() {
        Card card = new Card(Suit.HEARTS, 13);
        Suit suit = card.getSuit();
        int value = card.getValue();
        Assert.assertEquals(13, value);
        Assert.assertEquals(Suit.HEARTS, suit);
    }
}
