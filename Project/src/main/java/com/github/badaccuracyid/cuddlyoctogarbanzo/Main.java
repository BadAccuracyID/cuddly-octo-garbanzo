package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.database.Database;
import com.github.badaccuracyid.cuddlyoctogarbanzo.menus.impl.MainMenu;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Main instance = null;
    private final Scanner scanner;
    private final Database database;

    public Main() {
        instance = this;
        this.scanner = new Scanner(System.in);
        this.database = new Database();

        new MainMenu().open(MainMenu.class);
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                try {
                    instance.getDatabase().saveAllData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Utils.clearScreen();

            Utils.printLogo();
            System.out.println();
            System.out.println("                                - RedJackets 23-1 -");
            System.out.println("            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam a.");
        }));

        new Main();
    }

    public static Main getInstance() {
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Database getDatabase() {
        return database;
    }
}
