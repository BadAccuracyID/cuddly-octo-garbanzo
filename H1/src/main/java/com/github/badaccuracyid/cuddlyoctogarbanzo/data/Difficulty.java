package com.github.badaccuracyid.cuddlyoctogarbanzo.data;

public enum Difficulty {

    EASY(0.7, 1.3, 0.7),
    NORMAL(1, 1, 1),
    HARD(1.3, 0.7, 1.3);

    private final double enemyHealthModifier;
    private final double attackModifier;
    private final double moneyRewardModifier;

    Difficulty(double enemyHealthModifier, double attackModifier, double moneyRewardModifier) {
        this.enemyHealthModifier = enemyHealthModifier;
        this.attackModifier = attackModifier;
        this.moneyRewardModifier = moneyRewardModifier;
    }

    public double getEnemyHealthModifier() {
        return enemyHealthModifier;
    }

    public double getAttackModifier() {
        return attackModifier;
    }

    public double getMoneyRewardModifier() {
        return moneyRewardModifier;
    }
}
