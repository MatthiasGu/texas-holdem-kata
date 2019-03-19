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
        } else if (handContainsStraightFlush()) {
            return HandRanking.STRAIGHT_FLUSH;
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

    private boolean handContainsStraightFlush() {
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        sortHand();
        Card previousCardInSequence = cards.get(0);
        int numberOfConsecutiveStraightCards = 1;
        for (int i = 1; i < getSize(); i++) {
            Card currentCardInSequence = cards.get(i);
            if (currentCardInSequence.getSuit() == mostFrequentSuit) {
                if (twoSortedCardsAdjacentInRank(previousCardInSequence, currentCardInSequence)) {
                    numberOfConsecutiveStraightCards++;
                } else {
                    numberOfConsecutiveStraightCards = 0;
                }
            } else {
                numberOfConsecutiveStraightCards = 0;
            }
            if (numberOfConsecutiveStraightCards == 5) {
                return true;
            }
            previousCardInSequence = currentCardInSequence;
        }
        return false;
    }


    private boolean twoSortedCardsAdjacentInRank(Card card1, Card card2) {
        return ((card2.getRank().ordinal() == card1.getRank().ordinal() + 1) ||
                (card1.getRank() == Rank.ACE && card2.getRank() == Rank.DEUCE));
    }

    private boolean handContainsFlush() {
        Map<Suit, Integer> numberOfCardsOfEachSuitInHand = computeNumberOfCardsOfEachSuitInHand();
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        return numberOfCardsOfEachSuitInHand.get(mostFrequentSuit) >= 5;
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
