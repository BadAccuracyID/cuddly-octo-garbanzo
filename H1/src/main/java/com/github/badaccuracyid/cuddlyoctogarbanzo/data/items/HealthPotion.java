package com.github.badaccuracyid.cuddlyoctogarbanzo.data.items;

import com.github.badaccuracyid.cuddlyoctogarbanzo.data.PlayerData;

public class HealthPotion extends Item {

    public HealthPotion(int amount) {
        super(amount);
    }

    @Override
    public void doEffect(PlayerData playerData) {
        playerData.getJokeMon().heal(25);
        System.out.println("You used a health potion and healed 25 health!");
        System.out.println("Your JokeMon now has " + playerData.getJokeMon().getHealth() + " health.");
    }

}
