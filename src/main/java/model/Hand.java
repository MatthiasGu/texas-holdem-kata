package model;

import java.util.*;

public class Hand {

    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getSize() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
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

    public boolean containsAllCards(List<Card> cardsToCheck) {
        for (Card card: cardsToCheck) {
            if (!containsCard(card)) {
                return false;
            }
        }
        return true;
    }

    public HandRanking getRanking() {
        return computeRanking();
    }

    public void sortHand() {
        cards.sort(Card::compareTo);
    }

    private HandRanking computeRanking() {
        if (handContainsRoyalFlush()) {
            return HandRanking.ROYAL_FLUSH;
        }
        return HandRanking.HIGH_CARD;
    }

    private boolean handContainsRoyalFlush() {
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        List<Card> cardsRequired = Arrays.asList(
                new Card(mostFrequentSuit, Rank.TEN),
                new Card(mostFrequentSuit, Rank.JACK),
                new Card(mostFrequentSuit, Rank.QUEEN),
                new Card(mostFrequentSuit, Rank.KING),
                new Card(mostFrequentSuit, Rank.ACE));
        return containsAllCards(cardsRequired);
    }

    private Suit getMostFrequentSuitInHand() {
        Map<Suit, Integer> numberOfCardsOfEachSuitInHand = computeNumberOfCardsOfEachSuitInHand();
        int largestFrequency = 0;
        Suit mostFrequentSuit = Suit.CLUBS;
        for (Suit suit : numberOfCardsOfEachSuitInHand.keySet()) {
            int suitFrequency = numberOfCardsOfEachSuitInHand.get(suit);
            if (suitFrequency > largestFrequency) {
                largestFrequency = suitFrequency;
                mostFrequentSuit = suit;
            }
        }
        return mostFrequentSuit;
    }

    private Map<Suit, Integer> computeNumberOfCardsOfEachSuitInHand() {
        Map<Suit, Integer> numberOfCardsOfEachSuitInHand  = new HashMap<>();
        cards.forEach(card -> {
            Suit suit = card.getSuit();
            if (numberOfCardsOfEachSuitInHand.containsKey(suit)) {
                numberOfCardsOfEachSuitInHand.put(suit, numberOfCardsOfEachSuitInHand.get(suit) + 1);
            } else {
                numberOfCardsOfEachSuitInHand.put(suit, 1);
            }
        });
        return numberOfCardsOfEachSuitInHand;
    }

}
