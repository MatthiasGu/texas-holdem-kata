package model;

public class Player {

    private int playerNumber;
    private String playerName;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.playerName = String.format("Player %d", playerNumber);
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public String getPlayerName() {
        return playerName;
    }
}
