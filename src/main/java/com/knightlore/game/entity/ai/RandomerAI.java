package com.knightlore.game.entity.ai;

import java.util.Random;

import com.knightlore.game.entity.Direction;

/**
 * Determines the 'Randomer' enemy behaviour
 * @author Jacqui
 */
public class RandomerAI {

  private static Random random = new Random();
  public static int distance; // Technically game updates but this correlates to the distance moved
  public int accumulator;

  /**
   * Default constructor
   */
  public RandomerAI() {
    accumulator = 0;
    genDistance();
  }

  /**
   *
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

  private void genDistance() { distance = random.nextInt(200-10) + 10; }




}