package com.github.badaccuracyid.cuddlyoctogarbanzo;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Gender;
import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.Patient;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Sorter;
import com.github.badaccuracyid.cuddlyoctogarbanzo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final List<Patient> patientList = new ArrayList<>();

    public Main() {
        this.homeMenu();
    }

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Utils.clearScreen();

            Utils.printLogo();
            System.out.println();
            System.out.println("                                - RedJackets 23-1 -");
            System.out.println("            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam a.");
        }));

        new Main();
    }

    public void homeMenu() {
        int choice = 0;
        do {
            Utils.clearScreen();
            Utils.printLocalLogo();

            System.out.println("1. Add patient");
            System.out.println("2. View patients");
            System.out.println("3. Discharge Patient");
            System.out.println("4. Exit");
            System.out.print(">> ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }
        } while (choice < 1 || choice > 4);

        switch (choice) {
            case 1: {
                this.addPatient();
                break;
            }
            case 2: {
                this.viewPatients();
                break;
            }
            case 3: {
                this.dischargePatient();
                break;
            }
            case 4: {
                System.exit(0);
                break;
            }
        }
    }

    private void addPatient() {
        Utils.clearScreen();
        Utils.printLocalLogo();

        String patientName;
        do {
            System.out.print("Enter patient name [Starts with 'Mr. ' or 'Mrs. ']: ");
            patientName = scanner.nextLine();
        } while (!patientName.startsWith("Mr. ") && !patientName.startsWith("Mrs. "));

        String patientDisease;
        do {
            System.out.print("Enter patient disease [>= 10 characters]: ");
            patientDisease = scanner.nextLine();
        } while (patientDisease.length() < 10);

        Gender gender;
        if (patientName.startsWith("Mr. ")) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        String rawName = patientName.replaceFirst("Mr. ", "").replaceFirst("Mrs. ", "");

        Patient patient = new Patient(gender, rawName, patientDisease);
        patientList.add(patient);

        System.out.println("Patient added successfully!");

        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        this.homeMenu();
    }

    private void printPatients() {
        Utils.clearScreen();
        Utils.printLocalLogo();

        if (patientList.isEmpty()) {
            System.out.println("There are no patients.");
            System.out.println();
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            this.homeMenu();
            return;
        }

        Sorter.mergeSortPatients.accept(patientList);

        System.out.println("=============================================================");
        System.out.printf("| %-3s | %-24s | %-24s |\n", "No.", "Patient's Name", "Patient's Disease");
        System.out.println("=============================================================");
        int i = 1;
        for (Patient patient : patientList) {
            String patientName = patient.getGender().getPrefix() + patient.getName();
            System.out.printf("| %-3d | %-24s | %-24s |\n", i, patientName, patient.getDisease());
            i++;
        }
        System.out.println("=============================================================");
    }

    private void viewPatients() {
        this.printPatients();

        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        this.homeMenu();
    }

    private void dischargePatient() {
        this.printPatients();

        int choice = 0;
        do {
            System.out.print("Enter patient number to discharge: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }
        } while (choice < 1 || choice > patientList.size());

        int index = choice - 1;
        Patient patient = patientList.get(index);
        patientList.remove(index);

        String patientName = patient.getGender().getPrefix() + patient.getName();
        System.out.println(patientName + " is now healthy and has been discharged successfully!");

        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        this.homeMenu();
    }
}
