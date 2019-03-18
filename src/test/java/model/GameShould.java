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

    @Test
    public void drawPlayerHandsWhenGameStarts() {
        int numberOfPlayers = 2;
        Game game = new Game(numberOfPlayers);
        game.start();
        List<Player> players = game.getPlayers();
        int deckSize = game.getDeck().getSize();
        int player1HandSize = players.get(0).getHand().getSize();
        int player2HandSize = players.get(1).getHand().getSize();
        Assert.assertEquals(43, deckSize);
        Assert.assertEquals(2, player1HandSize);
        Assert.assertEquals(2, player2HandSize);
    }

}
