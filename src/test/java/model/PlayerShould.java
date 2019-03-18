package model;

import org.junit.Assert;
import org.junit.Test;

public class PlayerShould {

    @Test
    public void haveACorrectNameAndNumberWhenInitialised() {
        int playerNumber = 1;
        Player player = new Player(1);
        Assert.assertEquals(1, player.getPlayerNumber());
        Assert.assertEquals("Player 1", player.getPlayerName());
    }
}
