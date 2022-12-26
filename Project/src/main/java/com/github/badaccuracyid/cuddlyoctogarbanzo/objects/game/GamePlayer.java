package com.github.badaccuracyid.cuddlyoctogarbanzo.objects.game;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class GamePlayer {

    protected final List<Card> cards;

    public GamePlayer() {
        this.cards = new ArrayList<>();
    }

    public void giveCard(Card card) {
        this.cards.add(card);
    }

    public int getScore() {
        int score = 0;
        for (Card card : this.cards) {
            score += card.getRank().getScore();
        }

        return score;
    }

    public abstract void displayCards(boolean reveal);

}
