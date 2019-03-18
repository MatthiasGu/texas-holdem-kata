package model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getSize() {
        return this.cards.size();
    }

}
