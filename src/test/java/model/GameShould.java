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

}
