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
        deck.drawCard();
        Assert.assertEquals(51, deck.getSize());
    }

    @Test
    public void notHaveTheCardInTheDeckWhenItIsDrawn() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        Assert.assertEquals(false, deck.containsCard(card));
    }

    @Test
    public void drawGivenNumberOfCardsFromTheDeckWhenDrawCardIsCalled() {
        Deck deck = new Deck();
        deck.drawCards(5);
        Assert.assertEquals(47, deck.getSize());
    }
}
