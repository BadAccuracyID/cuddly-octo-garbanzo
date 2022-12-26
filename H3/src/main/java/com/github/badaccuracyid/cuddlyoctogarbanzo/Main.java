package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.database.RaceDatabase;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Player;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.RaceResult;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final RaceDatabase raceDatabase = new RaceDatabase();
    private final Player player = new Player("Player");

    public Main() {
        homePage();
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Utils.clearScreen();

            Utils.printLogo();
            System.out.println();
            System.out.println("                                - RedJackets 23-1 -");
            System.out.println("            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam a.");
        }));

        new Main();
    }

    private void printStats() {
        System.out.println("Balance: " + player.getMoney());
        System.out.println("Shoe Level: " + player.getShoeLevel());
        System.out.println("Leash Level: " + player.getLeashLevel());
    }

    private void homePage() {
        int choice = 0;
        do {
            Utils.clearScreen();
            System.out.println("Horse Gallop");
            System.out.println("====================");
            this.printStats();
            System.out.println("====================");
            System.out.println("1. Begin Race");
            System.out.println("2. View History");
            System.out.println("3. Upgrade Accessories");
            System.out.println("4. Exit");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1:
                beginRace();
                break;
            case 2:
                viewHistory();
                break;
            case 3:
                upgradeAccessories();
                break;
            case 4:
                System.exit(0);
                break;
        }
    }

    private void beginRace() {
        Match match = new Match(this.player, this.scanner, this.raceDatabase);
        match.startMatch();

        this.homePage();
    }

    private void viewHistory() {
        Utils.clearScreen();
        System.out.println("Race History");
        System.out.println("====================");

        List<RaceResult> raceResults = this.raceDatabase.getRaceResultList();
        if (raceResults.isEmpty()) {
            System.out.println("You haven't done any races yet!");
        } else {
            raceResults.forEach(it -> System.out.println(it.getState() + ", your last position was " + it.getPosition() + ", winner(s): " + it.getWinners()));
        }

        System.out.println();
        System.out.println("Press enter to continue...");
        this.scanner.nextLine();

        this.homePage();
    }

    private void upgradeAccessories() {
        int choice = 0;
        do {
            Utils.clearScreen();
            System.out.println("Upgrade Accessories");
            System.out.println("====================");
            this.printStats();
            System.out.println("====================");
            System.out.println("1. Upgrade Shoe");
            System.out.println("2. Upgrade Leash");
            System.out.println("3. Exit");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }
        } while (choice < 1 || choice > 3);

        System.out.println();
        switch (choice) {
            case 1: {
                if (player.getShoeLevel() >= 4) {
                    System.out.println("You have reached the maximum level for this accessory.");
                    System.out.println("Press enter to continue...");
                    this.scanner.nextLine();
                    this.upgradeAccessories();
                    return;
                }

                int price = 1000 * this.player.getShoeLevel();
                System.out.println("Upgrade shoe level " + player.getShoeLevel() + " to " + (player.getShoeLevel() + 1) + " for $" + price);
                System.out.println("Are you sure you want to upgrade? (yes/no)");
                System.out.print(">> ");

                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println();
                    if (player.getMoney() >= price) {
                        player.decrementMoney(price);
                        player.upgradeShoeLevel();

                        System.out.println("Successfully upgraded shoe level to " + player.getShoeLevel());
                    } else {
                        System.out.println("You don't have enough money to upgrade!");
                    }

                    System.out.println("Press enter to continue...");
                    this.scanner.nextLine();
                }

                this.upgradeAccessories();
                break;
            }
            case 2: {
                if (player.getLeashLevel() >= 3) {
                    System.out.println("You have reached the maximum leash level!");
                    System.out.println("Press enter to continue...");
                    this.scanner.nextLine();
                    this.upgradeAccessories();
                    break;
                }

                int price = 1200 * player.getLeashLevel();
                System.out.println("Upgrade leash level " + player.getLeashLevel() + " to " + (player.getLeashLevel() + 1) + " for $" + price);
                System.out.println("Are you sure you want to upgrade? (yes/no)");
                System.out.print(">> ");

                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.println();
                    if (player.getMoney() >= price) {
                        player.decrementMoney(price);
                        player.upgradeLeashLevel();

                        System.out.println("Successfully upgraded leash level to " + player.getLeashLevel());
                    } else {
                        System.out.println("You don't have enough money to upgrade!");
                    }
                    System.out.println("Press enter to continue...");
                    this.scanner.nextLine();
                }

                this.upgradeAccessories();
                break;
            }
            case 3: {
                this.homePage();
                break;
            }
        }

    }
}
