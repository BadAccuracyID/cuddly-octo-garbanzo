package com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl;

import com.github.badaccuracyid.cuddlyoctogarbanzo.game.Game;
import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.Arrays;

public class GameMenu extends Menu {

    @Override
    protected void showMenu() {
        Utils.clearScreen();

        int max = main.getDatabase().getCurrentPlayer().getScore();
        if (max <= 0) {
            System.out.println("=====================================");
            System.out.println("|           !!! ERROR !!!           |");
            System.out.println("=====================================");
            System.out.println("| Your account has reached 0 point  |");
            System.out.println("| and has been banned by the system |");
            System.out.println("=====================================");
            System.out.println();

            this.enterOrGoBack();
        }

        int bet = this.determineIntChoice(() -> System.out.print("Input your bet [max " + max + "]: "),
                Arrays.asList(
                        new IntRequirement(
                                input -> input > 0,
                                "[!] Bet must be greater than 0!"
                        ), new IntRequirement(
                                input -> input <= max,
                                "[!] Bet must be less than or equal to your current score (" + max + ")!"
                        )), false);

        new Game(main, bet).start();
    }

}
