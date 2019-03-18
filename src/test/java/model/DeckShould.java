package model;

import org.junit.Assert;
import org.junit.Test;

public class DeckShould {

    @Test
    public void initialiseWith52Cards() {
        Deck deck = new Deck();
        Assert.assertEquals(52, deck.getSize());
    }

    @Test
    public void drawACardFromTheDeckWhenDrawCardIsCalled() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        System.out.println(card);
        Assert.assertEquals(51, deck.getSize());
    }
}
