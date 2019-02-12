package com.knightlore.hud.engine;

public class Timer {

    private double lastLoopTime;
    private double startTime;
    
    public void init() {
        lastLoopTime = getTime();
        startTime = getTime();
    }
    
    public float getFullTime() {
    	double time = getTime();
    	float fullTime = (float) (time - startTime);
    	return fullTime;
    }

    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}