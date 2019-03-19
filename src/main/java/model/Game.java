package model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Deck deck;
    private List<Card> communityCards;

    public Game() {
        players = new ArrayList<>();
        deck = new Deck();
    }

    public Game(int numberOfPlayers) {
        players = createPlayers(numberOfPlayers);
        deck = new Deck();
    }

    public void start() {
        communityCards = deck.drawCards(5);
        drawPlayerHands();
        addCommunityCardsToAllPlayersHands();
    }

    private void drawPlayerHands() {
        players.forEach(player -> {
            List<Card> cardsToDraw = deck.drawCards(2);
            player.setHand(cardsToDraw);
        });
    }

    private void addCommunityCardsToAllPlayersHands() {
        players.forEach(player -> {
            Hand currentPlayersHand = player.getHand();
            communityCards.forEach(communityCard -> {
                currentPlayersHand.addCard(communityCard);
            });
        });
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

    public List<Card> getCommunityCards() {
        return communityCards;
    }

}
