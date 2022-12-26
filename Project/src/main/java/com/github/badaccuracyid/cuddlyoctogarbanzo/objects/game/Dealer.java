package com.github.badaccuracyid.cuddlyoctogarbanzo.objects.game;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Card;

public class Dealer extends GamePlayer {

    @Override
    public void displayCards(boolean reveal) {
        for (Card card : this.cards) {
            if (!reveal && this.cards.indexOf(card) > 0) {
                System.out.printf("%-3s", "???");
            } else {
                System.out.printf("%-3s", card.toString());
            }

            if (this.cards.indexOf(card) != this.cards.size() - 1) {
                System.out.print(" | ");
            }
        }
    }

}
