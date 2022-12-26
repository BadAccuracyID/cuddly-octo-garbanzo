package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.data.Difficulty;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.PlayerData;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.items.Item;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.Enemy;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.JokeMon;

import java.util.Random;
import java.util.Scanner;

public class Match {

    private final PlayerData playerData;
    private final Scanner scanner;
    private final Random random;
    private final JokeMon enemyJokeMon = new Enemy();
    private boolean isRunning = true;
    private int rewardMoney = 50;

    public Match(PlayerData playerData, Scanner scanner) {
        this.playerData = playerData;
        this.scanner = scanner;
        this.random = new Random();

        JokeMon playerJokeMon = playerData.getJokeMon();
        Difficulty difficulty = playerData.getDifficulty();

        enemyJokeMon.modifyHealth(difficulty.getEnemyHealthModifier());
        playerJokeMon.modifyDamage(difficulty.getAttackModifier());
        rewardMoney *= difficulty.getMoneyRewardModifier();
    }

    public void start() {
        Utils.clearScreen();
        System.out.println("Match started!");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        // gaming task
        while ((!enemyJokeMon.isDead() || !playerData.getJokeMon().isDead()) && isRunning) {
            userTurn();
            if (isRunning) {
                enemyTurn();
            }
        }
    }

    public void stopGame(int state) {
        Utils.clearScreen();
        isRunning = false;

        switch (state) {
            case 0: {
                System.out.println("Congratulations, you win!");
                playerData.incrementMoney(rewardMoney);
                System.out.println("You got " + rewardMoney + " joke-dollars!");
                break;
            }
            case 1: {
                System.out.println("You lost the game!");
                break;
            }
            case 2: {
                break;
            }
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }

    private void printStats() {
        JokeMon jokeMon = playerData.getJokeMon();
        System.out.println("--------------------");
        System.out.println("Your JokeMon    : " + jokeMon.getName());
        System.out.println("JokeMon Health  : " + jokeMon.getHealth());
        System.out.println("JokeMon Attack  : " + jokeMon.getDamage());
        System.out.println("Money           : " + playerData.getMoney());
        System.out.println();
        System.out.println("Enemy Health    : " + enemyJokeMon.getHealth());
        System.out.println("Enemy Attack    : " + enemyJokeMon.getDamage());
        System.out.println("--------------------");
    }

    private void userTurn() {
        Utils.clearScreen();
        System.out.println("--------------------");
        System.out.println("    Your turn!");

        printStats();

        int choice = 0;
        do {
            System.out.println("1. Attack");
            System.out.println("2. Escape");
            System.out.println("3. Use Item");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ignored) {
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                attack();
                break;
            case 2:
                escape();
                break;
            case 3:
                useItem();
                break;
        }
    }

    private void attack() {
        // 50:50 chance to hit
        if (random.nextBoolean()) {
            playerData.getJokeMon().attack(enemyJokeMon);
            System.out.println();
            System.out.println("JokeMon damaged enemy by " + playerData.getJokeMon().getDamage() + " damage!");
        } else {
            System.out.println();
            System.out.println("JokeMon missed!");
        }

        if (enemyJokeMon.isDead()) {
            System.out.println();
            System.out.println("Enemy died!");
            System.out.println("Press ENTER to continue...");
            scanner.nextLine();

            stopGame(0);
            return;
        }

        System.out.println();
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }

    private void escape() {
        if (playerData.getMoney() < 90) {
            // cannot escape
            System.out.println("You don't have enough money to escape!");
            System.out.println("You need 90 joke-dollar to escape!");

            System.out.println("Press ENTER to continue...");
            scanner.nextLine();
            return;
        }

        // if money is more than 90
        System.out.println("You escaped the game!");
        System.out.println("You lost 90 joke-dollar.");
        playerData.decrementMoney(90);

        System.out.println();
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();

        stopGame(2);
    }

    private void useItem() {
        Item healthPotion = playerData.getHealthPotion();
        Item attackPotion = playerData.getAttackPotion();
        if (healthPotion.getAmount() == 0 && attackPotion.getAmount() == 0) {
            System.out.println("You don't have any item.");
            System.out.println();
            System.out.println("Press ENTER to continue...");
            scanner.nextLine();

            userTurn();
            return;
        }

        int choice = 0;
        do {
            System.out.println("1. Health Potion (" + healthPotion.getAmount() + ")");
            System.out.println("2. Attack Potion (" + attackPotion.getAmount() + ")");
            System.out.println("3. Back");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ignored) {
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1: {
                if (healthPotion.getAmount() == 0) {
                    System.out.println("You don't have any health potion.");
                    System.out.println();
                    System.out.println("Press ENTER to continue...");
                    scanner.nextLine();
                    return;
                }

                healthPotion.doEffect(playerData);
                healthPotion.decrementAmount();

                System.out.println();
                System.out.println("Press ENTER to continue...");
                break;
            }
            case 2: {
                if (attackPotion.getAmount() == 0) {
                    System.out.println("You don't have any attack potion.");
                    System.out.println();
                    System.out.println("Press ENTER to continue...");
                    scanner.nextLine();
                    return;
                }

                attackPotion.doEffect(playerData);
                attackPotion.decrementAmount();

                System.out.println();
                System.out.println("Press ENTER to continue...");
                break;
            }
            case 3: {
                userTurn();
            }
        }
    }

    private void enemyTurn() {
        Utils.clearScreen();
        System.out.println("--------------------");
        System.out.println("    Enemy's turn!");

        printStats();
        // 50:50 chance to hit
        if (random.nextBoolean()) {
            enemyJokeMon.attack(playerData.getJokeMon());
            System.out.println();
            System.out.println("Enemy damaged JokeMon by " + enemyJokeMon.getDamage() + " damage!");
        } else {
            System.out.println();
            System.out.println("Enemy missed!");
        }

        if (playerData.getJokeMon().isDead()) {
            System.out.println();
            System.out.println("JokeMon died!");
            System.out.println("Press ENTER to continue...");
            scanner.nextLine();

            stopGame(1);
            return;
        }

        System.out.println();
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }

}
