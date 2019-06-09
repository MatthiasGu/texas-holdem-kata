# texas-holdem-kata

The purpose of this kata was to practise TDD. I felt that there sufficient complexity in a 7-card poker to make it a challenge. It is 
considerably more complicated than a 5-card poker. For example:
* It is much harder to efficiently find the hand ranking. For example, it is very easy to find a straight flush in a 5-card hand.
We can simply check if there is a straight and a flush. That does not work for a 7-card hand - it is possible for a hand to contain
both a straight and a flush, but no straight flush. For example, 2H, 3H, 4H, 5H, 6D, 10S, 10H. 
* We also need to keep track of kickers - cards that are used for tiebreak when 2 hands are equivalent. To do that, we need to keep track
on which cards make up the highest possible hand ranking and take the highest of the “unused” cards as the kicker. 
* Some cards are common for all players - the so-called community cards. They need to be reflected in all players’ hands.

These 3 challenges make it difficult to write a generic algorithm. I started with a simple algorithm that would check each possible
ranking one by one, starting from the highest (royal flush). If the ranking can be constructed with the cards in the hand, we assign the
ranking to the hand and stop the algorithm. This looks efficient at first, because we stop immediately at the first matching ranking.

However, there is a serious problem with this. Royal flush is extremely rare - there are only 4 ways to construct a royal flush and the
odds of having a royal flush is less than 1 in 30000 - meaning that a casual poker player may not even see one in your life,
let alone have it in their hand. In comparison, the chance that there is at least a pair in your handis 82%, i.e. you are more likely
to have something in your hand than nothing. Thus, most of the time it is a wasted effort checking for a royal flush.

Therefore, a more efficient way is dividing the possible hand rankings into two categories that have a logical ranking order:
1. Same rank - these are all the rankings that rely on having cards of the same rank. This group has a somewhat particular pecking order
that makes it rather difficult to check efficiently, because there are two possible groups:
  * Single rank group - one pair -> three of a kind -> four of a kind
  * Two rank groups - two pair -> full house
  
Since three of a kind ranks higher than two pair, but four of a kind ranks lower than full house, this grouping is awkward to check.
However, I think this grouping is not actually logically accurate. Full house does not follow from two pair, but rather from three of a
kind + one pair. This makes the groups as follows:
  * One pair -> Two pair
  * One pair -> three of a kind -> four of a kind
  * (One pair && three of a kind) -> full house
All of these groups conveniently share one prerequisite - having at least a pair in your hand, which is going to be this group’s
precondition.

2. Straight / Flush - both are required to go to straight flush and royal flush.

An important note - it is impossible to have a straight or flush in your hand, while also having a four of a kind or a full house.
That is because four of a kind only contributes 1 card to either straight or flush, while only leaving 3 remaining cards as an option
for the rest of the 4 cards needed to complete a straight or a flush. Full house potentially contributes 2 cards, but only leaves 2
spare cards.

Therefore, once we find a four of a kind or a full house, we do not need to check for straight or flush. We want to check the most
probable hands first, because we do not need to check the rare hands if their prerequisites are not met. On average, we will only need
5 operations (pair, two pair, three of a kind, flush, straight) to check all the possibilities. Unfortunately, I don’t think it is
possible to cut this number further, because flush and straight are mutually independent and so are three of a kind and two pair.
Since one pair is very likely to occur, we will have to check both these scenarios 80% of the time. Nevertheless, it is still a colossal
improvement over the previous algorithm which takes 9 steps until it gets to the one pair scenario. After refactoring the order of
operations, the runtime of the tests decreased by ~80%.







  
 


