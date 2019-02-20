package com.knightlore.client.gui.engine;

public class Timer {

    private double lastLoopTime;
    private double startTime;
    
    public void init() {
        lastLoopTime = getTime();
        startTime = getTime();
    }
    
    public float getGameTime() {
    	double time = getTime();
    	float gameTime = (float) (time - startTime);
    	return gameTime;
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