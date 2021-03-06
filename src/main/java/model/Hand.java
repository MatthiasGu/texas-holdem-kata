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
        int highestNumberOfCardsOfOneRankInHand = getHighestNumberOfCardsOfOneRankInHand();
        if (highestNumberOfCardsOfOneRankInHand >= 2) {
            return checkNonFlushOrStraightHandRankings(highestNumberOfCardsOfOneRankInHand);
        } else {
            HandRanking backupRanking =
                    new HandRanking(HandRankingCategory.HIGH_CARD, getKicker(Collections.emptyList()));
            return checkStraightAndFlushRankings(backupRanking);
        }
    }

    private HandRanking checkNonFlushOrStraightHandRankings(int highestNumberOfCardsOfOneRankInHand) {
        if (highestNumberOfCardsOfOneRankInHand == 2) {
            return checkStraightAndFlushRankings(checkPairRankings());
        } else if (highestNumberOfCardsOfOneRankInHand == 3) {
            return checkThreeOfAKindAndFullHouseRankings();
        }
        return rankHandAsFourOfAKindIfPresent().get();
    }

    private HandRanking checkPairRankings() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        if (numberOfCardsOfEachRankInHand.values().stream().filter(val -> val == 2).toArray().length >= 2) {
            return rankHandAsTwoPair();
        }
        return rankHandAsOnePair();
    }

    private HandRanking rankHandAsTwoPair() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        List<Rank> ranksUsedForPairs = new ArrayList<>();
        for (Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
            if (numberOfCardsOfEachRankInHand.get(rank) == 2) {
                ranksUsedForPairs.add(rank);
            }
        }
        ranksUsedForPairs.sort(Comparator.comparing(Rank::ordinal).reversed());
        Rank higherRank = ranksUsedForPairs.get(0);
        Rank lowerRank = ranksUsedForPairs.get(1);
        return new HandRanking(HandRankingCategory.TWO_PAIR, higherRank, lowerRank);
    }


    private HandRanking rankHandAsOnePair() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        Rank rankWithThePair = Rank.ACE;
        for (Rank rank : numberOfCardsOfEachRankInHand.keySet()) {
            if (numberOfCardsOfEachRankInHand.get(rank) == 2) {
                rankWithThePair = rank;
            }
        }
        List<Card> cardsUsedInRanking = getAllCardsOfGivenRankInHand(rankWithThePair);
        return new HandRanking(HandRankingCategory.ONE_PAIR, rankWithThePair, getKicker(cardsUsedInRanking));
    }

    private HandRanking checkThreeOfAKindAndFullHouseRankings() {
        Optional<HandRanking> fullHouseRankingIfPresent = rankHandAsFullHouseIfPresent();
        return fullHouseRankingIfPresent.orElseGet(
                () -> checkStraightAndFlushRankings(rankHandAsThreeOfAKindIfPresent().get()));
    }

    private HandRanking checkStraightAndFlushRankings(HandRanking backupRanking) {
        Optional <HandRanking> flushRankingIfPresent = rankHandAsFlushIfPresent();
        if (flushRankingIfPresent.isPresent()) {
            return checkStraightFlushRankings(flushRankingIfPresent.get());
        }
        return checkStraightRankings(backupRanking);
    }

    private HandRanking checkStraightRankings(HandRanking backupRanking) {
        Optional <HandRanking> straightRankingIfPresent = rankHandAsStraightIfPresent();
        return straightRankingIfPresent.orElse(backupRanking);
    }

    private HandRanking checkStraightFlushRankings(HandRanking backupRanking) {
        Optional <HandRanking> straightFlushRankingIfPresent = rankHandAsStraightFlushIfPresent();
        if (straightFlushRankingIfPresent.isPresent()) {
            return checkRoyalFlushRankings(straightFlushRankingIfPresent.get());
        }
        return backupRanking;
    }

    private HandRanking checkRoyalFlushRankings(HandRanking backupRanking) {
        Optional <HandRanking> royalFlushRankingIfPresent = rankHandAsRoyalFlushIfPresent();
        return royalFlushRankingIfPresent.orElse(backupRanking);
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
        return ((card1.getRank().ordinal() == card2.getRank().ordinal() + 1) ||
                (card2.getRank() == Rank.ACE && card1.getRank() == Rank.DEUCE));
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

    private int getHighestNumberOfCardsOfOneRankInHand() {
        Map<Rank, Integer> numberOfCardsOfEachRankInHand = computeNumberOfCardsOfEachRankInHand();
        return numberOfCardsOfEachRankInHand.values().stream().max(Comparator.comparing(Integer::intValue)).get();
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
