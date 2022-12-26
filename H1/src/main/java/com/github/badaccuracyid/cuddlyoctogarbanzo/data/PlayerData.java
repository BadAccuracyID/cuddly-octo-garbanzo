package com.github.badaccuracyid.cuddlyoctogarbanzo.data;

import com.github.badaccuracyid.cuddlyoctogarbanzo.data.items.AttackPotion;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.items.HealthPotion;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.items.Item;
import com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons.JokeMon;

public class PlayerData {

    private final String playerName;

    int money = 100;

    private Difficulty difficulty;
    private JokeMon jokeMon;

    private final Item healthPotion = new HealthPotion(0);
    private final Item attackPotion = new AttackPotion(0);

    public PlayerData(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMoney() {
        return money;
    }

    public void incrementMoney(int amount) {
        this.money += amount;
    }

    public void decrementMoney(int amount) {
        this.money -= amount;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public JokeMon getJokeMon() {
        return jokeMon;
    }

    public void setJokeMon(JokeMon jokeMon) {
        this.jokeMon = jokeMon;
    }

    public Item getHealthPotion() {
        return healthPotion;
    }

    public Item getAttackPotion() {
        return attackPotion;
    }

}
