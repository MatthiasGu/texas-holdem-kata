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
    public void drawCommunityCardsWhenGameStarts() {
        Game game = new Game();
        game.start();
        int deckSize = game.getDeck().getSize();
        int riverSize = game.getCommunityCards().size();
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
        Assert.assertEquals(7, player1HandSize);
        Assert.assertEquals(7, player2HandSize);
    }

    @Test
    public void notIncludeNonCommunityCardsFromOnePlayerInOtherPlayersHands() {
        int numberOfPlayers = 20;
        Game game = new Game (numberOfPlayers);
        game.start();
        List<Player> players = game.getPlayers();
        for (int i = 0; i < numberOfPlayers; i++) {
            Hand currentPlayersHand = players.get(i).getHand();
            List<Card> currentPlayersCardsInHand = currentPlayersHand.getCards().subList(0, 2);
            for (int j = i + 1; j < numberOfPlayers; j++) {
                Assert.assertEquals(false, players.get(j).getHand().containsAnyCard(currentPlayersCardsInHand));
            }
        }

    }

    @Test
    public void includeCommunityCardsInAllPlayersHands() {
        int numberOfPlayers = 2;
        Game game = new Game(numberOfPlayers);
        game.start();
        List<Player> players = game.getPlayers();
        List<Card> communityCards = game.getCommunityCards();
        Hand player1Hand = players.get(0).getHand();
        Hand player2Hand = players.get(1).getHand();
        communityCards.forEach(communityCard -> {
            Assert.assertEquals(true, player1Hand.containsCard(communityCard));
            Assert.assertEquals(true, player2Hand.containsCard(communityCard));
        });
    }

    @Test
    public void RankAllPlayersHandsWhenGameEnds() {
        int numberOfPlayers = 2;
        Game game = new Game(numberOfPlayers);
        game.start();
        game.end();
        List<Player> players = game.getPlayers();
        HandRanking player1HandRanking = players.get(0).getHandRanking();
        System.out.println(player1HandRanking);
        HandRanking player2HandRanking = players.get(1).getHandRanking();
        Assert.assertNotEquals(null, player1HandRanking);
        Assert.assertNotEquals(null, player2HandRanking);
    }

}
