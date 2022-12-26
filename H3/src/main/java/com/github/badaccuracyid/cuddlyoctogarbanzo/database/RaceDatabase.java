package com.github.badaccuracyid.cuddlyoctogarbanzo.database;

import com.github.badaccuracyid.cuddlyoctogarbanzo.objects.RaceResult;

import java.util.ArrayList;
import java.util.List;

public class RaceDatabase {

    private final List<RaceResult> raceResultList = new ArrayList<>();

    public void addRaceResult(RaceResult raceResult) {
        raceResultList.add(raceResult);
    }

    public List<RaceResult> getRaceResultList() {
        return raceResultList;
    }
}
