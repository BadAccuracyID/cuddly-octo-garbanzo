package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

import java.util.Objects;

public class Player {

    private final String name;
    private int money = 0;
    private int shoeLevel = 1;
    private int leashLevel = 1;

    private int position = 1;

    public Player(String name) {
        this.name = name;
    }

    public int getMoney() {
        return this.money;
    }

    public void incrementMoney(int money) {
        this.money += money;
    }

    public void decrementMoney(int money) {
        this.money -= money;
    }

    public int getShoeLevel() {
        return this.shoeLevel;
    }

    public void setShoeLevel(int shoeLevel) {
        this.shoeLevel = shoeLevel;
    }

    public void upgradeShoeLevel() {
        this.shoeLevel++;
    }

    public int getLeashLevel() {
        return this.leashLevel;
    }

    public void setLeashLevel(int leashLevel) {
        this.leashLevel = leashLevel;
    }

    public void upgradeLeashLevel() {
        this.leashLevel++;
    }

    public int getPosition() {
        return position;
    }

    public void resetPosition() {
        this.position = 1;
    }

    public void move() {
        this.position++;
    }

    public boolean canMove() {
        int tiredProbability = 25 - this.shoeLevel - this.leashLevel;
        return Math.random() * 100 > tiredProbability;
    }

    public boolean isWinner() {
        return this.position >= 40;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
