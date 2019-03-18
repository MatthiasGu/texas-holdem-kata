package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = initialiseStartingDeck();
    }

    private List<Card> initialiseStartingDeck() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        return cards;
    }

    public Card drawCard() {
        Random randomGenerator = new Random();
        int randomCardIndex = randomGenerator.nextInt(cards.size() - 1);
        return cards.remove(randomCardIndex);
    }

    public List<Card> drawCards(int numberOfCardsToDraw) {
        List<Card> cardsToDraw = new ArrayList<>();
        for (int i = 0; i < numberOfCardsToDraw; i++) {
            cardsToDraw.add(drawCard());
        }
        return cardsToDraw;
    }

    public boolean containsCard(Card cardToCheck) {
        Suit suit = cardToCheck.getSuit();
        Rank rank = cardToCheck.getRank();
        for (Card card: cards) {
            if (card.getRank() == rank && card.getSuit() == suit) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return cards.size();
    }
}
