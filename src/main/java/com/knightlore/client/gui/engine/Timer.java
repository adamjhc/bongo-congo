package com.knightlore.client.gui.engine;

public class Timer {

  private double lastLoopTime;
  private double startTime;

  public Timer() {
    lastLoopTime = getTime();
  }

  public void setStartTime() {
    startTime = getTime();
  }

  public float getGameTime() {
    double time = getTime();
    return (float) (time - startTime);
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

  private double getTime() {
    return System.nanoTime() / 1000_000_000.0;
  }
}
