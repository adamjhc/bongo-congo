package com.knightlore.game.entity.ai;

import java.util.Random;

import com.knightlore.game.entity.Direction;

/**
 * Determines the 'Randomer' enemy behaviour
 * @author Jacqui
 */
public class RandomerAI {

  private static Random random = new Random();
  public static int distance;
  public int accumulator;

  public RandomerAI() {
    accumulator = 0;
    genDistance();
  }


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