package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

public class Patient {

    private final Gender gender;
    private final String name, disease;

    public Patient(Gender gender, String name, String disease) {
        this.gender = gender;
        this.name = name;
        this.disease = disease;
    }

    public Gender getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getDisease() {
        return disease;
    }
}
