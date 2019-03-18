package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DeckShould {

    @Test
    public void initialiseWith52Cards() {
        Deck deck = new Deck();
        Assert.assertEquals(52, deck.getSize());
    }

    @Test
    public void drawACardFromTheDeckWhenDrawCardIsCalled() {
        Deck deck = new Deck();
        deck.drawCard();
        Assert.assertEquals(51, deck.getSize());
    }

    @Test
    public void drawGivenNumberOfCardsFromTheDeckWhenDrawCardIsCalled() {
        Deck deck = new Deck();
        deck.drawCards(5);
        Assert.assertEquals(47, deck.getSize());
    }
}
