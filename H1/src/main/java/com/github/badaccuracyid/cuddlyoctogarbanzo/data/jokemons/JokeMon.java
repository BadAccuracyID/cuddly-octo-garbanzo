package com.github.badaccuracyid.cuddlyoctogarbanzo.data.jokemons;

public abstract class JokeMon {

    private final String name;
    private int damage;

    private int health;

    public JokeMon(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = health;
    }

    public void modifyDamage(double modifier) {
        this.damage *= modifier;
    }

    public void modifyHealth(double modifier) {
        this.health *= modifier;
    }

    public void heal(int amount) {
        this.health += amount;
    }

    public void buffDamage(int amount) {
        this.damage += amount;
    }

    public void damage(int amount) {
        this.health -= amount;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void attack(JokeMon jokeMonTarget) {
        jokeMonTarget.damage(this.damage);
    }

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }
}
