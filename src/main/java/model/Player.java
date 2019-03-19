package model;

import java.util.List;

public class Player {

    private int playerNumber;
    private String playerName;
    private Hand hand;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.playerName = String.format("Player %d", playerNumber);
        this.hand = new Hand();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(List<Card> cards) {
        this.hand = new Hand(cards);
    }

    public HandRanking getHandRanking() {
        return hand.getRanking();
    }
}
