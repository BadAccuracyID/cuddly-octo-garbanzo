package com.github.badaccuracyid.cuddlyoctogarbanzo.objects;

public class RaceResult {

    private final String state, winners;
    private final int position;

    public RaceResult(String state, String winners, int position) {
        this.state = state;
        this.winners = winners;
        this.position = position;
    }

    public String getState() {
        return state;
    }

    public String getWinners() {
        return winners;
    }

    public int getPosition() {
        return position;
    }
}
