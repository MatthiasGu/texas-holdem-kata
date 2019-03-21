package model;

import java.util.*;
import java.util.stream.Collectors;

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

    private boolean listContainsCard(Card cardToCheck, List<Card> cardList) {
        Suit suit = cardToCheck.getSuit();
        Rank rank = cardToCheck.getRank();
        for (Card card: cardList) {
            if (card.getRank() == rank && card.getSuit() == suit) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kicker is the highest ranked card in hand that is not used for the current hand ranking.
     */
    private Rank getKicker(List<Card> cardsUsedInRanking) {
        sortHandByRank();
        for (int i = 0; i < cards.size(); i++) {
            Card currentHighestCard = cards.get(i);
            if (!listContainsCard(currentHighestCard, cardsUsedInRanking)) {
                return currentHighestCard.getRank();
            }
        }
        return cards.get(0).getRank();
    }

    private HandRanking computeHandRanking() {
        Optional<HandRanking> optionalHandRanking = rankHandAsRoyalFlushIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsStraightFlushIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsFourOfAKindIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsFullHouseIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsFlushIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsStraightIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsThreeOfAKindIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsTwoPairIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        optionalHandRanking = rankHandAsOnePairIfPresent();
        if (optionalHandRanking.isPresent()) {
            return optionalHandRanking.get();
        }
        return new HandRanking(HandRankingCategory.HIGH_CARD, getKicker(Collections.emptyList()));
    }

    private Optional<HandRanking> rankHandAsRoyalFlushIfPresent() {
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        List<Card> cardsRequired = Arrays.asList(
                new Card(mostFrequentSuit, Rank.TEN),
                new Card(mostFrequentSuit, Rank.JACK),
                new Card(mostFrequentSuit, Rank.QUEEN),
                new Card(mostFrequentSuit, Rank.KING),
                new Card(mostFrequentSuit, Rank.ACE));
        return containsAllCards(cardsRequired) ?
                Optional.of(new HandRanking(HandRankingCategory.ROYAL_FLUSH)) :
                Optional.empty();
    }

    private Optional<HandRanking> rankHandAsStraightFlushIfPresent() {
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        sortHandBySuit();
        List<Card> cardsUsedInRanking = new ArrayList<>();
        Card previousCardInSequence = cards.get(0);
        cardsUsedInRanking.add(previousCardInSequence);
        int numberOfConsecutiveStraightCards = 1;
        for (int i = 1; i < getSize(); i++) {
            Card currentCardInSequence = cards.get(i);
            if (currentCardInSequence.getSuit() == mostFrequentSuit) {
                if (twoSortedCardsAdjacentInRank(previousCardInSequence, currentCardInSequence)) {
                    numberOfConsecutiveStraightCards++;

                } else {
                    numberOfConsecutiveStraightCards = 1;
                    cardsUsedInRanking.clear();
                }
            } else {
                numberOfConsecutiveStraightCards = 1;
                cardsUsedInRanking.clear();
            }
            cardsUsedInRanking.add(currentCardInSequence);
            if (numberOfConsecutiveStraightCards == 5) {
                return Optional.of(new HandRanking(
                        HandRankingCategory.STRAIGHT_FLUSH,
                        cardsUsedInRanking.get(0).getRank(),
                        getKicker(cardsUsedInRanking)
                ));
            }
            previousCardInSequence = currentCardInSequence;
        }
        return Optional.empty();
    }

    private boolean twoSortedCardsAdjacentInRank(Card card1, Card card2) {
        return ((card2.getRank().ordinal() == card1.getRank().ordinal() + 1) ||
                (card1.getRank() == Rank.ACE && card2.getRank() == Rank.DEUCE));
    }

    private Optional<HandRanking> rankHandAsFourOfAKindIfPresent() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        if (numberOfCardsOfEachRankInHand.containsValue(4)) {
            for(Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
                if (numberOfCardsOfEachRankInHand.get(rank) == 4) {
                    List<Card> cardsUsedInRanking = getAllCardsOfGivenRankInHand(rank);
                    return Optional.of(new HandRanking(
                            HandRankingCategory.FOUR_OF_A_KIND, rank, getKicker(cardsUsedInRanking)));
                }
            }
        }
        return Optional.empty();
    }

    private List<Card> getAllCardsOfGivenRankInHand(Rank rank) {
        return cards.stream().filter(card -> card.getRank() == rank).collect(Collectors.toList());
    }

    private Optional<HandRanking> rankHandAsFullHouseIfPresent() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        if (numberOfCardsOfEachRankInHand.containsValue(3) && numberOfCardsOfEachRankInHand.containsValue(2)) {
            Rank firstTiebreak = null;
            Rank secondTiebreak = null;
            for (Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
                if (numberOfCardsOfEachRankInHand.get(rank) == 3) {
                    firstTiebreak = rank;
                } else if (numberOfCardsOfEachRankInHand.get(rank) == 2) {
                    secondTiebreak = rank;
                }
            }
            return Optional.of(new HandRanking(HandRankingCategory.FULL_HOUSE, firstTiebreak, secondTiebreak));
        }
        return Optional.empty();
    }

    private Optional<HandRanking> rankHandAsFlushIfPresent() {
        Map<Suit, Integer> numberOfCardsOfEachSuitInHand = computeNumberOfCardsOfEachSuitInHand();
        Suit mostFrequentSuit = getMostFrequentSuitInHand();
        if (numberOfCardsOfEachSuitInHand.get(mostFrequentSuit) >= 5) {
            List<Card> cardsUsedInRanking = getAllCardsOfGivenSuitInHand(mostFrequentSuit);
            cardsUsedInRanking.sort(Card.rankOrder);
            cardsUsedInRanking = cardsUsedInRanking.subList(0, 5);
            Rank highestRankUsed = cardsUsedInRanking.get(0).getRank();
            return Optional.of(
                    new HandRanking(HandRankingCategory.FLUSH, highestRankUsed, getKicker(cardsUsedInRanking)));
        }
        return Optional.empty();
    }

    private List<Card> getAllCardsOfGivenSuitInHand(Suit suit) {
        return cards.stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }

    private Optional<HandRanking> rankHandAsStraightIfPresent() {
        sortHandByRank();
        List<Card> cardsUsedInRanking = new ArrayList<>();
        Card previousCardInSequence = cards.get(0);
        cardsUsedInRanking.add(previousCardInSequence);
        int numberOfConsecutiveStraightCards = 1;
        for (int i = 1; i < getSize(); i++) {
            Card currentCardInSequence = cards.get(i);
            if (twoSortedCardsAdjacentInRank(previousCardInSequence, currentCardInSequence)) {
                numberOfConsecutiveStraightCards++;
            } else {
                numberOfConsecutiveStraightCards = 1;
                cardsUsedInRanking.clear();
            }
            cardsUsedInRanking.add(currentCardInSequence);
            if (numberOfConsecutiveStraightCards == 5) {
                return Optional.of(new HandRanking(
                        HandRankingCategory.STRAIGHT,
                        cardsUsedInRanking.get(0).getRank(),
                        getKicker(cardsUsedInRanking)
                ));
            }
            previousCardInSequence = currentCardInSequence;
        }
        return Optional.empty();
    }

    private Optional<HandRanking> rankHandAsThreeOfAKindIfPresent() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        if (numberOfCardsOfEachRankInHand.containsValue(3)) {
            for (Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
                if (numberOfCardsOfEachRankInHand.get(rank) == 3) {
                    List<Card> cardsUsedInRanking = getAllCardsOfGivenRankInHand(rank);
                    return Optional.of(new HandRanking(
                            HandRankingCategory.THREE_OF_A_KIND, rank, getKicker(cardsUsedInRanking)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    private Optional<HandRanking> rankHandAsTwoPairIfPresent() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        if (numberOfCardsOfEachRankInHand.values().stream().filter(val -> val == 2).toArray().length == 2) {
            List<Rank> ranksUsedForPairs = new ArrayList<>();
            for (Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
                if (numberOfCardsOfEachRankInHand.get(rank) == 2) {
                    ranksUsedForPairs.add(rank);
                }
            }
            Rank higherRank = ranksUsedForPairs.get(0).ordinal() > ranksUsedForPairs.get(1).ordinal() ?
                    ranksUsedForPairs.get(0) :
                    ranksUsedForPairs.get(1);
            Rank lowerRank = ranksUsedForPairs.get(0).ordinal() < ranksUsedForPairs.get(1).ordinal() ?
                    ranksUsedForPairs.get(0) :
                    ranksUsedForPairs.get(1);
            return Optional.of(new HandRanking(
                    HandRankingCategory.TWO_PAIR, higherRank, lowerRank));
        }
        return Optional.empty();
    }


    private Optional<HandRanking> rankHandAsOnePairIfPresent() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        if (numberOfCardsOfEachRankInHand.containsValue(2)) {
            for (Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
                if (numberOfCardsOfEachRankInHand.get(rank) == 2) {
                    List<Card> cardsUsedInRanking = getAllCardsOfGivenRankInHand(rank);
                    return Optional.of(new HandRanking(
                            HandRankingCategory.ONE_PAIR, rank, getKicker(cardsUsedInRanking)
                    ));
                }
            }
        }
        return Optional.empty();
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
