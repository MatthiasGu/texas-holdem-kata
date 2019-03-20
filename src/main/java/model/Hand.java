package model;

import java.util.*;

public class Hand {

    private List<Card> cards;
    private HandRanking handRanking;

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

    public boolean containsAnyCard(List<Card> cardsToCheck) {
        for (Card card: cardsToCheck) {
            if (containsCard(card)) {
                return true;
            }
        }
        return false;
    }

    public void rankHand() {
        handRanking = computeHandRanking();
    }

    public HandRanking getHandRanking() {
        return handRanking;
    }

    public void sortHandBySuit() {
        cards.sort(Card.suitOrder);
    }

    public void sortHandByRank() {
        cards.sort(Card.rankOrder);
    }

    private HandRanking computeHandRanking() {
        if (handContainsRoyalFlush()) {
            return HandRanking.ROYAL_FLUSH;
        } else if (handContainsStraightFlush()) {
            return HandRanking.STRAIGHT_FLUSH;
        } else if (handContainsFourOfAKind()) {
            return HandRanking.FOUR_OF_A_KIND;
        } else if (handContainsFullHouse()) {
            return HandRanking.FULL_HOUSE;
        } else if (handContainsFlush()) {
            return HandRanking.FLUSH;
        } else if (handContainsStraight()) {
            return HandRanking.STRAIGHT;
        } else if (handContainsThreeOfAKind()) {
            return HandRanking.THREE_OF_A_KIND;
        } else if (handContainsTwoPair()) {
            return HandRanking.TWO_PAIR;
        } else if (handContainsOnePair()) {
            return HandRanking.ONE_PAIR;
        }
        return HandRanking.HIGH_CARD;
    }

    private boolean handContainsRoyalFlush() {
        if (handContainsStraightFlush()) {
            Suit mostFrequentSuit = getMostFrequentSuitInHand();
            List<Card> cardsRequired = Arrays.asList(
                    new Card(mostFrequentSuit, Rank.TEN),
                    new Card(mostFrequentSuit, Rank.JACK),
                    new Card(mostFrequentSuit, Rank.QUEEN),
                    new Card(mostFrequentSuit, Rank.KING),
                    new Card(mostFrequentSuit, Rank.ACE));
            return containsAllCards(cardsRequired);
        }
        return false;
    }

    private boolean handContainsStraightFlush() {
        if (handContainsFlush()) {
            Suit mostFrequentSuit = getMostFrequentSuitInHand();
            sortHandBySuit();
            Card previousCardInSequence = cards.get(0);
            int numberOfConsecutiveStraightCards = 1;
            for (int i = 1; i < getSize(); i++) {
                Card currentCardInSequence = cards.get(i);
                if (currentCardInSequence.getSuit() == mostFrequentSuit) {
                    if (twoSortedCardsAdjacentInRank(previousCardInSequence, currentCardInSequence)) {
                        numberOfConsecutiveStraightCards++;
                    } else {
                        numberOfConsecutiveStraightCards = 1;
                    }
                } else {
                    numberOfConsecutiveStraightCards = 1;
                }
                if (numberOfConsecutiveStraightCards == 5) {
                    return true;
                }
                previousCardInSequence = currentCardInSequence;
            }
        }
        return false;
    }

    private boolean twoSortedCardsAdjacentInRank(Card card1, Card card2) {
        return ((card2.getRank().ordinal() == card1.getRank().ordinal() + 1) ||
                (card1.getRank() == Rank.ACE && card2.getRank() == Rank.DEUCE));
    }

    private boolean handContainsFourOfAKind() {
        return computeNumberOfCardsOfEachRankInHand().containsValue(4);
    }

    private boolean handContainsFullHouse() {
        return handContainsThreeOfAKind() && handContainsOnePair();
    }

    private boolean handContainsFlush() {
        Map<Suit, Integer> numberOfCardsOfEachSuitInHand = computeNumberOfCardsOfEachSuitInHand();
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        return numberOfCardsOfEachSuitInHand.get(mostFrequentSuit) >= 5;
    }

    private boolean handContainsStraight() {
        sortHandByRank();
        Card previousCardInSequence = cards.get(0);
        int numberOfConsecutiveStraightCards = 1;
        for (int i = 1; i < getSize(); i++) {
            Card currentCardInSequence = cards.get(i);
            if (twoSortedCardsAdjacentInRank(previousCardInSequence, currentCardInSequence)) {
                numberOfConsecutiveStraightCards++;
            } else {
                numberOfConsecutiveStraightCards = 1;
            }
            if (numberOfConsecutiveStraightCards == 5) {
                return true;
            }
            previousCardInSequence = currentCardInSequence;
        }
        return false;
    }

    private boolean handContainsThreeOfAKind() {
        return computeNumberOfCardsOfEachRankInHand().containsValue(3);
    }

    private boolean handContainsTwoPair() {
        return computeNumberOfCardsOfEachRankInHand().values().stream().filter(val -> val == 2).toArray().length == 2;
    }

    private boolean handContainsOnePair() {
        return computeNumberOfCardsOfEachRankInHand().containsValue(2);
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

    private Map<Rank, Integer> computeNumberOfCardsOfEachRankInHand() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = new HashMap<>();
        cards.forEach(card -> {
            Rank rank = card.getRank();
            if (numberOfCardsOfEachRankInHand.containsKey(rank)) {
                numberOfCardsOfEachRankInHand.put(rank, numberOfCardsOfEachRankInHand.get(rank) + 1);
            } else {
                numberOfCardsOfEachRankInHand.put(rank, 1);
            }
        });
        return numberOfCardsOfEachRankInHand;
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
