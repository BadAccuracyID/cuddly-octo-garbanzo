package com.github.badaccuracyid.cuddlyoctogarbanzo.objects.game;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Card;

public class Player extends GamePlayer {

    @Override
    public void displayCards(boolean reveal) {
        for (Card card : this.cards) {
            System.out.printf("%-3s", card.toString());

            if (this.cards.indexOf(card) != this.cards.size() - 1) {
                System.out.print(" | ");
            }
        }
    }

}
