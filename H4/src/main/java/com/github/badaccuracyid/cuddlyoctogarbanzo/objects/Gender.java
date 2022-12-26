package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

public enum Gender {

    MALE("Mr. "),
    FEMALE("Mrs. ");

    private final String prefix;

    Gender(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
