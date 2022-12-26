package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

public class Card {

    public enum Suit {
        HEARTS(Utils.HEART),
        DIAMONDS(Utils.DIAMOND),
        CLUBS(Utils.CLUB),
        SPADES(Utils.SPADE);

        private final String symbol;

        Suit(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    public enum Rank {
        TWO(2, "2"),
        THREE(3, "3"),
        FOUR(4, "4"),
        FIVE(5, "5"),
        SIX(6, "6"),
        SEVEN(7, "7"),
        EIGHT(8, "8"),
        NINE(9, "9"),
        TEN(10, "10"),
        JACK(10, "J"),
        QUEEN(10, "Q"),
        KING(10, "K"),
        ACE(11, "A");

        private final int score;
        private final String name;

        Rank(int score, String name) {
            this.score = score;
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
    }

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return this.getRank().getName() + this.getSuit().getSymbol();
    }
}
