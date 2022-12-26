package com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl;

import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.List;

public class RegisterMenu extends Menu {

    protected RegisterMenu() {
        super();
    }

    @Override
    protected void showMenu() {
        Utils.clearScreen();

        String username = this.determineStrChoice(() -> System.out.print("Input your username [4 ... 10 characters]: "),
                List.of(new StrRequirement(
                        input -> input.length() >= 4 && input.length() <= 10,
                        "[!] Username must be between 4 and 10 characters!"
                ), new StrRequirement(
                        input -> main.getDatabase().getPlayerData(input) == null,
                        "[!] Username already taken!"
                )), false);

        String password = this.determineStrChoice(() -> System.out.print("Input your password [8 ... 16 alphanumeric characters]: "),
                List.of(new StrRequirement(
                        input -> input.length() >= 8 && input.length() <= 16,
                        "[!] Password must be between 8 and 16 characters!"
                ), new StrRequirement(
                        this::isAlphanumeric,
                        "[!] Password must be alphanumeric!"
                )), false);

        PlayerData playerData = new PlayerData(username);
        playerData.setPassword(password);
        playerData.setScore(100);
        main.getDatabase().savePlayerData(playerData);

        main.getDatabase().setCurrentPlayer(playerData);
        this.open(LoggedInMenu.class);
    }

    private boolean isAlphanumeric(String str) {
        // check if string is strictly alphanumeric
        boolean hasLetter = false;
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
                break;
            }
        }

        boolean hasDigit = false;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }

        boolean hasSymbol = false;
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
                break;
            }
        }

        return hasLetter && hasDigit && !hasSymbol;
    }

}
