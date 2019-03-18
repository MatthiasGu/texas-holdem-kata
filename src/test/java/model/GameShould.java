package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GameShould {

    @Test
    public void haveNoPlayersByDefault() {
        Game game = new Game();
        List<Player> players = game.getPlayers();
        Assert.assertEquals(0, players.size());
    }

    @Test
    public void bePossibleToInitialiseWithOnePlayer() {
        Game game = new Game(1);
        List<Player> players = game.getPlayers();
        Assert.assertEquals(1, players.size());
    }

    @Test
    public void bePossibleToInitialiseWithMultiplePlayers() {
        Game game = new Game(5);
        List<Player> players = game.getPlayers();
        Assert.assertEquals(5, players.size());
    }

    @Test
    public void initialiseWithAFullStartingDeck() {
        Game game = new Game();
        Deck deck = game.getDeck();
        Assert.assertEquals(52, deck.getSize());
    }

    @Test
    public void drawARiverWhenGameStarts() {
        Game game = new Game();
        game.start();
        int deckSize = game.getDeck().getSize();
        int riverSize = game.getRiver().size();
        Assert.assertEquals(47, deckSize);
        Assert.assertEquals(5, riverSize);
    }

}
