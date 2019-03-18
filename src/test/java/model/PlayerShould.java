package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PlayerShould {

    @Test
    public void haveACorrectNameAndNumberWhenInitialised() {
        int playerNumber = 1;
        Player player = new Player(1);
        Assert.assertEquals(1, player.getPlayerNumber());
        Assert.assertEquals("Player 1", player.getPlayerName());
    }

    @Test
    public void haveAnEmptyHandByDefault() {
        int playerNumber = 1;
        Player player = new Player(1);
        Hand hand = player.getHand();
        Assert.assertEquals(0, hand.getSize());
    }
}
