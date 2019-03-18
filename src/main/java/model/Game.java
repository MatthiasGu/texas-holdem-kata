package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    private List<Card> river;

    public Game() {
        players = new ArrayList<>();
        deck = new Deck();
    }

    public Game(int numberOfPlayers) {
        players = createPlayers(numberOfPlayers);
        deck = new Deck();
    }

    public void start() {
        river = deck.drawCards(5);
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
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Card> getRiver() {
        return river;
    }

}
