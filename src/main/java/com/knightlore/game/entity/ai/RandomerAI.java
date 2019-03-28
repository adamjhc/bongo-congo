package com.knightlore.game.entity.ai;

import java.util.Random;
import com.knightlore.game.entity.Direction;

/**
 * Determines the 'Randomer' enemy behaviour
 * @author Jacqui Henes
 */
public class RandomerAI {

  private static Random random = new Random();
  private static int distance; // Technically game updates but this correlates to the distance moved
  private int accumulator;

  /**
   * Default constructor
   */
  public RandomerAI() {
    accumulator = 0;
    genDistance();
  }

  /**
   * For a randomly selected amount of game updates, move forward, and then
   * switch to a new random direction.
   * @param direction Current enemy direction
   * @return Direction to be moved in (may be the same!)
   */
  public Direction pathfind(Direction direction) {
    if (accumulator >= distance) {
      direction = direction.getRandom();
      genDistance();
      accumulator = 0;
    } else {
      accumulator++;
    }
    return direction;
  }

  /**
   * Generates a random distance between 10-200 for the Randomer to move before
   * changing direction.
   */
  private void genDistance() { distance = random.nextInt(200-10) + 10; }




}