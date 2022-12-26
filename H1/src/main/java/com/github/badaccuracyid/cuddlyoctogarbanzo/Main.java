package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.data.Difficulty;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.Bulbasaur;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.Charmander;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.JokeMon;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.Squirtle;

import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private PlayerData playerData;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Utils.clearScreen();

            Utils.printLogo();
            System.out.println();
            System.out.println("                                - RedJackets 23-1 ~!");
            System.out.println("            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam a.");
        }));

        new Main();
    }

    public Main() {
        registerPage();
    }

    private void registerPage() {
        Utils.clearScreen();
        String name;
        do {
            System.out.println("JokeMon");
            System.out.println("====================");
            System.out.println();

            System.out.print("Input your name [>= 2 words]: ");
            name = scanner.nextLine();
        } while (name.split(" ").length < 2);

        playerData = new PlayerData(name);
        homePage();
    }

    private void homePage() {
        int choice = 0;
        do {
            Utils.clearScreen();
            System.out.println("Welcome, " + playerData.getPlayerName() + "!");
            System.out.println("1. Play");
            System.out.println("2. Shop");
            System.out.println("3. Quit");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ignored) {
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1: {
                playPage();
                break;
            }
            case 2: {
                shopPage();
                break;
            }
            case 3: {
                System.out.println("Goodbye, " + playerData.getPlayerName() + "!");
                System.exit(0);
                break;
            }
        }
    }

    private void playPage() {
        int choice = 0;
        do {
            Utils.clearScreen();
            System.out.println("-------------");
            System.out.println("Choose 1 JokeMon");
            System.out.println("1. Charmander (health: 100, attack: 20)");
            System.out.println("2. Squirtle (health: 70, attack: 40)");
            System.out.println("3. Bulbasaur (health: 200, attack: 10)");
            System.out.print("Input only 1 to 3: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ignored) {
            }
        } while (choice < 1 || choice > 3);

        JokeMon jokeMon;
        switch (choice) {
            case 1:
                jokeMon = new Charmander();
                break;
            case 2:
                jokeMon = new Squirtle();
                break;
            case 3:
                jokeMon = new Bulbasaur();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        playerData.setJokeMon(jokeMon);

        System.out.println();
        System.out.println("You choose " + jokeMon.getName() + "!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        Difficulty difficulty;
        do {
            Utils.clearScreen();
            System.out.println("-------------");
            System.out.println("Choose difficulty");
            System.out.println("1. Easy");
            System.out.println("2. Normal");
            System.out.println("3. Hard");
            System.out.print("Input only 'easy' or 'normal' or 'hard': ");

            try {
                difficulty = Difficulty.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (Exception ignored) {
            }
        } while (true);
        playerData.setDifficulty(difficulty);

        System.out.println();
        System.out.println("You choose " + difficulty.name().toLowerCase() + " difficulty!");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        Match match = new Match(playerData, scanner);
        match.start();

        homePage();
    }

    private void shopPage() {
        System.out.println("Money   : " + playerData.getMoney());
        if (playerData.getMoney() < 50) {
            System.out.println("You need at least 50 joke-dollars to shop.");

            System.out.println();
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            return;
        }

        int choice = 0;
        do {
            Utils.clearScreen();
            System.out.println("Money   : " + playerData.getMoney());
            System.out.println("-------------");
            System.out.println("1. Health Potion (+25 health, 50 joke-dollars)");
            System.out.println("2. Attack Potion (+25 attack, 75 joke-dollars)");
            System.out.println("3. Exit");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ignored) {
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1: {
                if (playerData.getMoney() < 50) {
                    System.out.println("You don't have enough money to buy this item.");
                    break;
                }

                playerData.decrementMoney(50);
                playerData.getHealthPotion().incrementAmount();
                System.out.println("You bought 1 health potion!");
                break;
            }
            case 2: {
                if (playerData.getMoney() < 75) {
                    System.out.println("You don't have enough money to buy this item.");
                    break;
                }

                playerData.decrementMoney(75);
                playerData.getAttackPotion().incrementAmount();
                System.out.println("You bought 1 attack potion!");
                break;
            }
            case 3: {
                break;
            }
        }

        homePage();
    }

}

