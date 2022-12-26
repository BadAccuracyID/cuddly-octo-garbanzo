package com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl;

import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.List;

public class LoginMenu extends Menu {

    protected LoginMenu() {
        super();
    }

    @Override
    protected void showMenu() {
        Utils.clearScreen();

        String username = this.determineStrChoice(() -> System.out.print("Input your username: "),
                List.of(new StrRequirement(
                        input -> input.length() >= 4 && input.length() <= 10,
                        "[!] Username must be between 4 and 10 characters!"
                )), false);

        String password = this.determineStrChoice(() -> System.out.print("Input your password: "),
                List.of(new StrRequirement(
                        input -> input.length() >= 8 && input.length() <= 16,
                        "[!] Password must be between 8 and 16 characters!"
                )), false);

        PlayerData playerData = main.getDatabase().getPlayerData(username);
        if (playerData == null || !playerData.getPassword().equals(password)) {
            System.out.println("[!] Invalid username or password!");
            System.out.println();

            this.enterOrGoBack();
            return;
        }

        main.getDatabase().setCurrentPlayer(playerData);
        this.open(LoggedInMenu.class);
    }

}
