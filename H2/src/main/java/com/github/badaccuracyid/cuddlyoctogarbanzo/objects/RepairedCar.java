package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

public class RepairedCar extends Car {

    private int reparationCost;
    private int reparationTime;

    public RepairedCar(Car car) {
        this.setCarBrand(car.getCarBrand());
        this.setCarName(car.getCarName());
        this.setCarType(car.getCarType());
        this.setOwnerName(car.getOwnerName());
    }

    public RepairedCar() {
    }

    public int getReparationCost() {
        return reparationCost;
    }

    public void setReparationCost(int reparationCost) {
        this.reparationCost = reparationCost;
    }

    public int getReparationTime() {
        return reparationTime;
    }

    public void setReparationTime(int reparationTime) {
        this.reparationTime = reparationTime;
    }
}
