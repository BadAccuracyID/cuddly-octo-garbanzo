package com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl;

import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.PlayerData;

import java.io.IOException;
import java.util.Arrays;

public class LoggedInMenu extends Menu {

    public LoggedInMenu() {
        super();
    }

    @Override
    protected void showMenu() {
        int choice = this.determineIntChoice(() -> {
            System.out.println("=====================");
            PlayerData currentPlayer = main.getDatabase().getCurrentPlayer();
            System.out.printf("| Hello, %-10s |\n", currentPlayer.getUsername());
            System.out.printf("| Points: %-9s |\n", currentPlayer.getScore());
            System.out.println("=====================");
            System.out.println("| 1. Play           |");
            System.out.println("| 2. High-score     |");
            System.out.println("| 3. Save & logout  |");
            System.out.println("=====================");
            System.out.print("Choose [1 - 3] >> ");
        }, Arrays.asList(
                new IntRequirement(
                        input -> input > 0,
                        "[!] Input must be greater than 0!"
                ), new IntRequirement(
                        input -> input < 4,
                        "[!] Input must be less than 4!"
                )), true);

        switch (choice) {
            case 1:
                this.open(GameMenu.class);
                break;
            case 2:
                this.open(HighScoreMenu.class);
                break;
            case 3:
                try {
                    main.getDatabase().saveAllData();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                main.getDatabase().setCurrentPlayer(null);
                this.open(MainMenu.class);
                break;
        }

    }


}
