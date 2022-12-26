package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Car;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.CarType;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.RepairedCar;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.FileUtils;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Utils.clearScreen();

            Utils.printLogo();
            System.out.println();
            System.out.println("                                - RedJackets 23-1 ~!");
            System.out.println("            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam a.");
        }));

        new Main();
    }

    public Main() {
        this.homePage();
    }

    public void homePage() {
        int choice = 0;
        do {
            Utils.clearScreen();
            System.out.println("Welcome to BWengkel!");
            System.out.println("====================");
            System.out.println("1. Add car to repair list");
            System.out.println("2. Repair car");
            System.out.println("3. Take out car");
            System.out.println("4. Exit");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1: {
                this.addCar();
                break;
            }
            case 2: {
                this.repairCar();
                break;
            }
            case 3: {
                this.takeOutCar();
                break;
            }
            case 4: {
                System.exit(0);
                break;
            }
        }
    }

    private void addCar() {
        Utils.clearScreen();

        Car car = new Car();

        String carOwner;
        do {
            System.out.println("Add car to repair list");
            System.out.println("=======================");
            System.out.println();

            System.out.print("Input car owner name [starts with 'Mr. /Mrs. ']: ");
            carOwner = scanner.nextLine();
        } while (!carOwner.startsWith("Mr. ") && !carOwner.startsWith("Mrs. "));
        System.out.println("Car owner name: " + carOwner);
        System.out.println();
        car.setOwnerName(carOwner);

        String carName;
        do {
            System.out.print("Input car name [>= 3 characters]: ");
            carName = scanner.nextLine();
        } while (carName.length() < 3);
        System.out.println("Car name: " + carName);
        System.out.println();
        car.setCarName(carName);

        String carBrand;
        do {
            System.out.print("Input car brand [>= 5 characters]: ");
            carBrand = scanner.nextLine();
        } while (carBrand.length() < 5);
        System.out.println("Car brand: " + carBrand);
        System.out.println();
        car.setCarBrand(carBrand);

        String carType;
        do {
            System.out.print("Input car type ['Sedan', 'Coupe' or 'SUV']: ");
            carType = scanner.nextLine();
        } while (!carType.equals("Sedan") && !carType.equals("Coupe") && !carType.equals("SUV"));
        System.out.println("Car type: " + carType);
        System.out.println();
        car.setCarType(CarType.valueOf(carType.toUpperCase()));


        FileUtils.appendCarToFile("reparationList.txt", car);

        System.out.println("Car added to repair list!");
        System.out.println("Press enter to continue...");
        scanner.nextLine();

        this.homePage();
    }

    private void repairCar() {
        Utils.clearScreen();

        List<Car> cars = FileUtils.readCarsFromFile("reparationList.txt");
        if (cars.isEmpty()) {
            System.out.println("There are no cars to repair!");
            System.out.println("Press enter to continue...");
            scanner.nextLine();

            this.homePage();
            return;
        }

        System.out.println("BWengkel's car list");
        System.out.println("====================");
        System.out.println();
        System.out.println("=======================================================================");
        System.out.printf("| %-3s | %-20s | %-12s | %-12s | %-8s |", "No.", "Owner", "Car Name", "Car Brand", "Car Type");
        System.out.println();
        System.out.println("=======================================================================");

        int i = 1;
        for (Car car : cars) {
            System.out.printf("| %-3d | %-20s | %-12s | %-12s | %-8s |", i, car.getOwnerName(), car.getCarName(), car.getCarBrand(), car.getCarType().toString());
            System.out.println();
            i++;
        }
        System.out.println("=======================================================================");

        System.out.println();
        System.out.println();
        int choice = 0;
        do {
            System.out.print("Choose car's index to be repair ['0' to cancel]: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }

            if ((choice < 0 || choice > cars.size())) {
                System.out.println("Invalid index! Please input a number between 0 and " + cars.size());
                System.out.println();
            }
        } while (choice < 0 || choice > cars.size());

        if (choice == 0) {
            this.homePage();
            return;
        }

        Utils.clearScreen();

        Car car = cars.get(choice - 1);

        int typePrice = car.getCarType().getPrice();
        // random from 10000 to 50000
        int mechanicPrice = (int) (Math.random() * 40000) + 10000;
        // random from 1 to 10
        int reparationTime = (int) (Math.random() * 10) + 1;

        int totalPrice = (typePrice * reparationTime) + mechanicPrice;

        System.out.println("Repairing car");
        System.out.println("====================");
        System.out.println();
        System.out.println("Car owner name: " + car.getOwnerName());
        System.out.println("Car name: " + car.getCarName());
        System.out.println("Car brand: " + car.getCarBrand());
        System.out.println("Car type: " + car.getCarType().toString());
        System.out.println("--------------------");
        // pricing
        System.out.println("Car type price: " + typePrice);
        System.out.println("Mechanic price: " + mechanicPrice);
        System.out.println("Reparation time: " + reparationTime);
        System.out.println("--------------------");
        System.out.println("Total price: " + totalPrice);
        System.out.println("Time to fix: " + reparationTime + " days");
        System.out.println();

        RepairedCar repairedCar = new RepairedCar(car);
        repairedCar.setReparationCost(totalPrice);
        repairedCar.setReparationTime(reparationTime);

        FileUtils.appendFixedCarToFile("takeCarList.txt", repairedCar);
        FileUtils.removeCarFromFile("reparationList.txt", cars, car);

        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();

        this.homePage();
    }

    private void takeOutCar() {
        Utils.clearScreen();

        List<RepairedCar> cars = FileUtils.readFixedCarsFromFile("takeCarList.txt");
        if (cars.isEmpty()) {
            System.out.println("There are no cars to take out!");
            System.out.println("Press enter to continue...");
            scanner.nextLine();

            this.homePage();
            return;
        }

        System.out.println("BWengkel's repaired car list");
        System.out.println("============================");
        System.out.println();
        System.out.println("===========================================================================================================");
        System.out.printf("| %-3s | %-20s | %-12s | %-12s | %-8s | %-15s | %-15s |", "No.", "Owner", "Car Name", "Car Brand", "Car Type", "Reparation Cost", "Reparation Time");
        System.out.println();
        System.out.println("===========================================================================================================");

        int i = 1;
        for (RepairedCar car : cars) {
            System.out.printf("| %-3d | %-20s | %-12s | %-12s | %-8s | %-15d | %-15s |", i, car.getOwnerName(), car.getCarName(), car.getCarBrand(), car.getCarType().toString(), car.getReparationCost(), (car.getReparationTime() + " days"));
            System.out.println();
            i++;
        }
        System.out.println("===========================================================================================================");

        System.out.println();
        System.out.println();
        int choice = 0;
        do {
            System.out.print("Choose car's index to be taken out ['0' to cancel]: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }

            if ((choice < 0 || choice > cars.size())) {
                System.out.println("Invalid index! Please input a number between 0 and " + cars.size());
                System.out.println();
            }
        } while (choice < 0 || choice > cars.size());

        if (choice == 0) {
            this.homePage();
            return;
        }

        Utils.clearScreen();

        RepairedCar car = cars.get(choice - 1);
        FileUtils.removeFixedCarsFromFile("takeCarList.txt", cars, car);

        System.out.println("Car taken out!");
        System.out.println("Press enter to continue...");
        scanner.nextLine();

        this.homePage();
    }
}
