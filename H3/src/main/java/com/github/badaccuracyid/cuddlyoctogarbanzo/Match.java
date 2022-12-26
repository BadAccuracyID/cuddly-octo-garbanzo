package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.database.RaceDatabase;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Player;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.RaceResult;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Match {

    private final Player player;
    private final Scanner scanner;
    private final RaceDatabase raceDatabase;

    private final Player bot1;
    private final Player bot2;

    private final int fencePosition;
    private final char[][] map;

    public Match(Player player, Scanner scanner, RaceDatabase raceDatabase) {
        this.player = player;
        this.scanner = scanner;
        this.raceDatabase = raceDatabase;

        this.bot1 = new Player("Bot1");
        this.bot1.setShoeLevel((int) (Math.random() * player.getShoeLevel()) + 1);
        this.bot1.setLeashLevel((int) (Math.random() * player.getLeashLevel()) + 1);

        this.bot2 = new Player("Bot2");
        this.bot2.setShoeLevel((int) (Math.random() * player.getShoeLevel()) + 1);
        this.bot2.setLeashLevel((int) (Math.random() * player.getLeashLevel()) + 1);

        this.fencePosition = 10;
        this.map = new char[7][52];

        for (int i = 0; i < 52; i++) {
            map[0][i] = '#';
            map[6][i] = '#';
        }

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < 52; j++) {
                map[i][j] = ' ';
            }

            map[i][0] = '#';
            map[i][51] = '#';
        }

        for (int i = 1; i < 51; i++) {
            map[2][i] = '-';
            map[4][i] = '-';
        }

        String str = "Player";
        for (int i = 0; i < str.length(); i++) {
            map[1][i + 2] = str.charAt(i);
        }
        map[1][fencePosition] = '|';
        str = "Bot1";
        for (int i = 0; i < str.length(); i++) {
            map[3][i + 2] = str.charAt(i);
        }
        map[3][fencePosition] = '|';
        str = "Bot2";
        for (int i = 0; i < str.length(); i++) {
            map[5][i + 2] = str.charAt(i);
        }
        map[5][10] = '|';
    }

    public void startMatch() {
        this.printMap();

        System.out.println("Press enter to start the race...");
        scanner.nextLine();

        // biar keren :v
        // dan biar ga busy wait
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        try {
            executor.scheduleAtFixedRate(() -> {
                if (player.canMove()) {
                    player.move();
                }
                if (bot1.canMove()) {
                    bot1.move();
                }
                if (bot2.canMove()) {
                    bot2.move();
                }

                Match.this.printMap();
                if (player.isWinner() || bot1.isWinner() || bot2.isWinner()) {
                    executor.shutdown();
                }
            }, 0, 200, TimeUnit.MILLISECONDS).get();
        } catch (InterruptedException | ExecutionException | CancellationException ignored) {
        }

//        while (!player.isWinner() && !bot1.isWinner() && !bot2.isWinner()) {
//            if (player.canMove()) {
//                player.movePosition();
//            }
//            if (bot1.canMove()) {
//                bot1.movePosition();
//            }
//            if (bot2.canMove()) {
//                bot2.movePosition();
//            }
//
//            this.printMap();
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        this.checkWinners();
        this.stopMatch();
    }

    public void stopMatch() {
        player.resetPosition();

        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }

    private void printMap() {
        Utils.clearScreen();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 52; j++) {
                if (player.getPosition() + fencePosition == j && i == 1) {
                    System.out.print("P");
                } else if (bot1.getPosition() + fencePosition == j && i == 3) {
                    System.out.print("B");
                } else if (bot2.getPosition() + fencePosition == j && i == 5) {
                    System.out.print("B");
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void checkWinners() {
        List<Player> winners = this.getWinners();

        if (winners.size() > 1) {
            // draw/tie
            StringBuilder winnerNames = new StringBuilder();
            for (Player winner : winners) {
                if (winners.indexOf(winner) == winners.size() - 1) {
                    winnerNames.append(" and ");
                } else if (winners.indexOf(winner) != 0) {
                    winnerNames.append(", ");
                }

                winnerNames.append(winner.getName());
            }

            System.out.println("Race ended, Draw!");
            System.out.println("Winners: " + winnerNames);

            if (winners.contains(player)) {
                // player also won
                int rewardMoney = player.getPosition() * 100;
                player.incrementMoney(rewardMoney);
                System.out.println();
                System.out.println("You got " + rewardMoney + "$ for making " + player.getPosition() + " meters!");
            }

            RaceResult raceResult = new RaceResult("Draw", winnerNames.toString(), player.getPosition());
            this.raceDatabase.addRaceResult(raceResult);
            return;
        }

        if (winners.contains(player)) {
            // player wins
            player.incrementMoney(12000);

            System.out.println("Race ended, you won!");
            System.out.println();
            System.out.println("You won 12000$");

            RaceResult raceResult = new RaceResult("Win", player.getName(), player.getPosition());
            this.raceDatabase.addRaceResult(raceResult);
        } else {
            // player loses
            int rewardMoney = player.getPosition() * 100;
            player.incrementMoney(rewardMoney);

            System.out.println("Race ended, you lost!");
            StringBuilder winnerNames = new StringBuilder();
            for (Player winner : winners) {
                winnerNames.append(winner.getName());
            }
            System.out.println("Winner: " + winnerNames);
            System.out.println();
            System.out.println("You got " + rewardMoney + "$ for making " + player.getPosition() + " meters!");

            RaceResult raceResult = new RaceResult("Lost", winnerNames.toString(), player.getPosition());
            this.raceDatabase.addRaceResult(raceResult);
        }
    }

    private List<Player> getWinners() {
        List<Player> winners = new ArrayList<>();
        if (player.isWinner()) {
            winners.add(player);
        }
        if (bot1.isWinner()) {
            winners.add(bot1);
        }
        if (bot2.isWinner()) {
            winners.add(bot2);
        }
        return winners;
    }
}
