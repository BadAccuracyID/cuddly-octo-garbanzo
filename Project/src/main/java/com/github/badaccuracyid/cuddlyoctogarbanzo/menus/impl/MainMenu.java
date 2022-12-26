package com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl;

import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.Arrays;

public class MainMenu extends Menu {

    public MainMenu() {
        super();

        new LoginMenu();
        new RegisterMenu();

        new LoggedInMenu();
        new HighScoreMenu();
        new GameMenu();
    }

    @Override
    protected void showMenu() {
        int choice = this.determineIntChoice(() -> {
            System.out.println("=====================");
            System.out.println("|" + Utils.HEART + "    RedJack    " + Utils.SPADE + " |");
            System.out.println("|" + Utils.DIAMOND + "    Card Game   " + Utils.CLUB + " |");
            System.out.println("=====================");
            System.out.println("| 1. Login          |");
            System.out.println("| 2. Register       |");
            System.out.println("| 3. Exit           |");
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
                this.open(LoginMenu.class);
                break;
            case 2:
                this.open(RegisterMenu.class);
                break;
            case 3:
                System.exit(0);
                break;
        }

    }

}
