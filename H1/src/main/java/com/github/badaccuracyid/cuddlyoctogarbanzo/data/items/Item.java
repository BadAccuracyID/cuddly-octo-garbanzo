package com.github.badaccuracyid.cuddlyoctogarbanzo.data.items;

import com.github.badaccuracyid.cuddlyoctogarbanzo.data.PlayerData;

public abstract class Item {

    private int amount;

    public Item(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void incrementAmount() {
        this.amount++;
    }

    public void decrementAmount() {
        this.amount--;
    }

    public abstract void doEffect(PlayerData playerData);

}
