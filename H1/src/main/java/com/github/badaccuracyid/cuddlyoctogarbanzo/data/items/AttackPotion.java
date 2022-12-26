package com.github.badaccuracyid.cuddlyoctogarbanzo.data.items;

import com.github.badaccuracyid.cuddlyoctogarbanzo.data.PlayerData;

public class AttackPotion extends Item {

    public AttackPotion(int amount) {
        super(amount);
    }

    @Override
    public void doEffect(PlayerData playerData) {
        playerData.getJokeMon().buffDamage(25);
        System.out.println("You used an attack potion and increased your damage by 25!");
        System.out.println("Your JokeMon now has " + playerData.getJokeMon().getDamage() + " damage.");
    }

}
