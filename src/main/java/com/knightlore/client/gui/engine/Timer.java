package com.knightlore.client.gui.engine;

/**
 * Keeps track of all the loop timing and game time
 * 
 * @author Joseph
 *
 */
public class Timer {

	/** Last update loop time */
  private double lastLoopTime;
  /** Game start time */
  private double startTime;

  /**
   * Initialise timer
   * 
   * @author Joseph
   * 
   */
  public Timer() {
    lastLoopTime = getTime();
  }

  /**
   * Set start time to current time
   * 
   * @author Joseph
   * 
   */
  public void setStartTime() {
    startTime = getTime();
  }
  
  /**
   * Set start time to zero
   * 
   * @author Joseph
   * 
   */
  public void resetStartTime() {
  	startTime = 0;
  }
  
  /**
   * Return start time
   * 
   * @return StartTime
   * @author Joseph
   * 
   */
  public double getStartTime() {
  	return startTime;
  }

  /**
   * Returns time since game started
   * 
   * @return Time - startTime
   * @author Joseph
   * 
   */
  public float getGameTime() {
    double time = getTime();
    return (float) (time - startTime);
  }

  /**
   * Gets the time since the last loop
   * Resets the lastLoopTime
   * 
   * @return ElapsedTime
   * @author Joseph
   * 
   */
  public float getElapsedTime() {
    double time = getTime();
    float elapsedTime = (float) (time - lastLoopTime);
    lastLoopTime = time;
    return elapsedTime;
  }

  /**
   * Returns the last loop time
   * 
   * @return LastLoopTime
   * @author Joseph
   * 
   */
  public double getLastLoopTime() {
    return lastLoopTime;
  }

  /**
   * Get the current time
   * 
   * @return Time
   * @author Joseph
   * 
   */
  private double getTime() {
    return System.nanoTime() / 1000_000_000.0;
  }
}
