package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

public enum CarType {

    SEDAN(50000),
    COUPE(30000),
    SUV(45000);

    private final int price;

    CarType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
