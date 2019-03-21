package model;

public class HandRanking implements Comparable<HandRanking> {
    private HandRankingCategory handRankingCategory;
    private Rank firstTiebreak;
    private Rank secondTiebreak;
    public HandRanking(HandRankingCategory handRankingCategory, Rank firstTiebreak, Rank secondTiebreak) {
        this.handRankingCategory = handRankingCategory;
        this.firstTiebreak = firstTiebreak;
        this.secondTiebreak = secondTiebreak;
    }

    public HandRanking(HandRankingCategory handRankingCategory, Rank tiebreak) {
        this.handRankingCategory = handRankingCategory;
        this.firstTiebreak = tiebreak;
    }

    public HandRanking(HandRankingCategory handRankingCategory) {
        this.handRankingCategory = handRankingCategory;
    }

    public HandRankingCategory getHandRankingCategory() {
        return handRankingCategory;
    }

    @Override
    public int compareTo(HandRanking handRanking) {
        int rankingComparison = Integer.compare(
                this.handRankingCategory.ordinal(), handRanking.handRankingCategory.ordinal());
        if (rankingComparison == 0) {
            int firstTiebreakComparison = Integer.compare(
                    this.firstTiebreak.ordinal(), handRanking.firstTiebreak.ordinal());
            if (firstTiebreakComparison == 0) {
                return Integer.compare(this.secondTiebreak.ordinal(), handRanking.secondTiebreak.ordinal());
            }
            return firstTiebreakComparison;
        }
        return rankingComparison;
    }
}
