package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class HandShould {

    @Test
    public void haveNoCardsByDefault() {
        Hand hand = new Hand();
        List<Card> cardsInHand = hand.getCards();
        Assert.assertEquals(0, cardsInHand.size());
    }
}
