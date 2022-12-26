package com.github.badaccuracyid.cuddlyoctogarbanzo.utils;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Car;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.CarType;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.RepairedCar;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void appendCarToFile(String fileName, Car car) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String content = car.getOwnerName() + ";" + car.getCarName() + ";" + car.getCarBrand() + ";" + car.getCarType();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(content);
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static List<Car> readCarsFromFile(String fileName) {
        List<Car> cars = new ArrayList<>();

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String read;
            while ((read = reader.readLine()) != null) {
                String[] split = read.split(";");
                Car car = new Car();
                car.setOwnerName(split[0]);
                car.setCarName(split[1]);
                car.setCarBrand(split[2]);
                car.setCarType(CarType.valueOf(split[3]));
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void removeCarFromFile(String fileName, List<Car> carList, Car car) {
        boolean remove = carList.remove(car);
        if (!remove) {
            System.exit(-1);
        }

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Car car1 : carList) {
                String content = car1.getOwnerName() + ";" + car1.getCarName() + ";" + car1.getCarBrand() + ";" + car1.getCarType();
                writer.append(content);
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void appendFixedCarToFile(String fileName, RepairedCar car) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String content = car.getOwnerName() + ";" + car.getCarName() + ";" + car.getCarBrand() + ";" + car.getCarType() + ";" + car.getReparationCost() + ";" + car.getReparationTime();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(content);
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static List<RepairedCar> readFixedCarsFromFile(String fileName) {
        List<RepairedCar> cars = new ArrayList<>();

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String read;
            while ((read = reader.readLine()) != null) {
                String[] split = read.split(";");
                RepairedCar car = new RepairedCar();
                car.setOwnerName(split[0]);
                car.setCarName(split[1]);
                car.setCarBrand(split[2]);
                car.setCarType(CarType.valueOf(split[3]));
                car.setReparationCost(Integer.parseInt(split[4]));
                car.setReparationTime(Integer.parseInt(split[5]));
                cars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void removeFixedCarsFromFile(String fileName, List<RepairedCar> repairedCars, RepairedCar repairedCar) {
        boolean remove = repairedCars.remove(repairedCar);
        if (!remove) {
            System.exit(-1);
        }

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (RepairedCar car : repairedCars) {
                String content = car.getOwnerName() + ";" + car.getCarName() + ";" + car.getCarBrand() + ";" + car.getCarType() + ";" + car.getReparationCost() + ";" + car.getReparationTime();
                writer.append(content);
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
