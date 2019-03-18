package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PlayerShould {

    @Test
    public void haveACorrectNameAndNumberWhenInitialised() {
        int playerNumber = 1;
        Player player = new Player(playerNumber);
        Assert.assertEquals(playerNumber, player.getPlayerNumber());
        Assert.assertEquals("Player 1", player.getPlayerName());
    }

    @Test
    public void haveAnEmptyHandByDefault() {
        int playerNumber = 1;
        Player player = new Player(playerNumber);
        Hand hand = player.getHand();
        Assert.assertEquals(0, hand.getSize());
    }
}
