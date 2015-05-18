package com.appdynamics.loadgen;

/**
 * Created by abey.tom on 11/1/14.
 */
public class TimeTracker {
    private long startTime;

    public void start(){
        startTime = System.currentTimeMillis();
    }

    public long end(){
        long l = System.currentTimeMillis() - startTime;

        return l;
    }
}
