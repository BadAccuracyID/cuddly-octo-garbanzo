package com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl;

import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.Menu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.SorterUtils;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.List;

public class HighScoreMenu extends Menu {

    @Override
    protected void showMenu() {
        Utils.clearScreen();

        System.out.println("===========================");
        System.out.println("|        HIGHSCORE        |");
        System.out.println("===========================");
        System.out.printf("| %-10s | %-10s |\n", "Username", "Points");
        System.out.println("===========================");

        List<PlayerData> loadedData = main.getDatabase().getLoadedData();
        SorterUtils.mergeSortData.accept(loadedData);

        loadedData.forEach(data -> System.out.printf("| %-10s | %-10s |\n", data.getUsername(), data.getScore()));
        System.out.println("===========================");
        System.out.println();

        this.enterOrGoBack();
    }
}
