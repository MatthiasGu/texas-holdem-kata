package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;

    public Game() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
    }

    public Game(int numberOfPlayers) {
        this.players = createPlayers(numberOfPlayers);
        this.deck = new Deck();
    }

    private List<Player> createPlayers(int numberOfPlayers) {
        List<Player> createdPlayers = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            Player player = new Player(i);
            createdPlayers.add(player);
        }
        return createdPlayers;
    }

    public List<Player> getPlayers() {
        return this.players;

    }

    public Deck getDeck() {
        return deck;
    }

}
