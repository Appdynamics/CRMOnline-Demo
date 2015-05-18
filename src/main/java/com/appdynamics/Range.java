package com.appdynamics;

/**
 * Created by abey.tom on 11/1/14.
 */
public class Range {

    public Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    private int lower;
    private int upper;

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }
}
