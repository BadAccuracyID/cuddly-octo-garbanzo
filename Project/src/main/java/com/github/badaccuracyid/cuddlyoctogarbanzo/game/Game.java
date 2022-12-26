package com.github.badaccuracyid.cuddlyoctogarbanzo.game;

import com.github.badaccuracyid.cuddlyoctogarbanzo.Main;
import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl.LoggedInMenu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl.MainMenu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Card;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.game.Dealer;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.game.GamePlayer;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.game.Player;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final List<Card> cards;
    private final Main main;
    private final PlayerData playerData;
    private final int bet;

    private final GamePlayer player, dealer;

    public Game(Main main, int bet) {
        this.cards = new ArrayList<>();
        this.main = main;
        this.playerData = main.getDatabase().getCurrentPlayer();
        this.bet = bet;

        this.player = new Player();
        this.dealer = new Dealer();

        this.initCards();
    }

    public void start() {
        // give 2 random cards to each player
        this.player.giveCard(this.getRandomCard());
        this.player.giveCard(this.getRandomCard());
        this.dealer.giveCard(this.getRandomCard());
        this.dealer.giveCard(this.getRandomCard());

        this.displayTable(false);
        this.displayChoices();
    }

    private void initCards() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                this.cards.add(new Card(suit, rank));
            }
        }
    }

    private Card getRandomCard() {
        int index = (int) (Math.random() * this.cards.size());
        Card card = this.cards.get(index);
        this.cards.remove(index);
        return card;
    }

    private void displayTable(boolean reveal) {
        Utils.clearScreen();

        System.out.println("Dealer Cards: ");
        this.dealer.displayCards(reveal);
        System.out.println();
        System.out.println("Your Cards: ");
        this.player.displayCards(reveal);
        System.out.println();
        System.out.println();
    }

    private void displayChoices() {
        System.out.println("====================");
        System.out.println("| Choose your move |");
        System.out.println("====================");
        System.out.println("| 1. Hit           |");
        System.out.println("| 2. Stand         |");
        System.out.println("====================");

        int choice = 0;
        do {
            System.out.print("Choose[1 - 2] >> ");
            try {
                choice = Integer.parseInt(main.getScanner().nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice!");
            }
        } while (choice < 1 || choice > 2);

        System.out.println();
        if (choice == 1) {
            this.hit();
        } else {
            this.stand(player);
        }
    }

    private void hit() {
        System.out.println("You choose to hit!");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
        }

        int dealerScore = this.dealer.getScore();
        if (dealerScore < 17) {
            this.stand(dealer);
            return;
        }

        GameResult gameResult = this.checkResult();
        this.processResult(gameResult);
    }

    private void stand(GamePlayer requesterPlayer) {
        System.out.println(requesterPlayer == player ? "You choose to stand!" : "Dealer choose to stand!");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
        }

        if (requesterPlayer == this.player) {
            this.player.giveCard(this.getRandomCard());

            GameResult gameResult = this.checkResult();
            if (gameResult == GameResult.PLAYER_BUSTED) {
                this.processResult(gameResult);
                return;
            }

            this.dealer.giveCard(this.getRandomCard());
            gameResult = this.checkResult();
            if (gameResult == GameResult.DEALER_BUSTED) {
                this.processResult(gameResult);
                return;
            }
        } else {
            this.dealer.giveCard(this.getRandomCard());

            GameResult gameResult = this.checkResult();
            if (gameResult == GameResult.DEALER_BUSTED) {
                this.processResult(gameResult);
                return;
            }

            this.player.giveCard(this.getRandomCard());
            gameResult = this.checkResult();
            if (gameResult == GameResult.PLAYER_BUSTED) {
                this.processResult(gameResult);
                return;
            }
        }

        this.displayTable(false);
        this.displayChoices();
    }

    private GameResult checkResult() {
        int playerScore = this.player.getScore();
        int dealerScore = this.dealer.getScore();

        if (playerScore > 21) {
            return GameResult.PLAYER_BUSTED;
        } else if (dealerScore > 21) {
            return GameResult.DEALER_BUSTED;
        } else if (playerScore == dealerScore) {
            return GameResult.TIE;
        } else if (playerScore > dealerScore) {
            return GameResult.PLAYER_WON;
        } else {
            return GameResult.DEALER_WON;
        }
    }

    private void processResult(GameResult gameResult) {
        this.displayTable(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("========================================");

        switch (gameResult) {
            case PLAYER_BUSTED: {
                System.out.println("[!] " + playerData.getUsername() + " busted, you lost " + this.bet + " points!");
                playerData.setScore(playerData.getScore() - this.bet);
                break;
            }
            case DEALER_BUSTED: {
                int reward = this.bet + this.bet;
                System.out.println("[!] Dealer busted, you won " + reward + " points!");
                playerData.setScore(playerData.getScore() + reward);
                break;
            }
            case PLAYER_WON: {
                int reward = this.bet + this.bet;
                System.out.println("[!] " + playerData.getUsername() + " won, you won " + reward + " points!");
                playerData.setScore(playerData.getScore() + reward);
                break;
            }
            case DEALER_WON: {
                System.out.println("[!] Dealer won, you lost " + this.bet + " points!");
                playerData.setScore(playerData.getScore() - this.bet);
                break;
            }
            case TIE: {
                System.out.println("[!] It's tie, you got nothing");
                break;
            }
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        main.getScanner().nextLine();

        Menu.openMenu(LoggedInMenu.class, MainMenu.class);
    }

    private enum GameResult {
        PLAYER_BUSTED,
        DEALER_BUSTED,
        PLAYER_WON,
        DEALER_WON,
        TIE
    }
}
