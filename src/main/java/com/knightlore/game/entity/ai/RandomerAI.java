package com.knightlore.game.entity.ai;

import java.util.List;
import java.util.Random;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

// Walks in a random direction for a set distance before changing direction and continuing
public class RandomerAI {

  private static Random random = new Random();
  public static int distance; // number of game updates before changing direction
  public int accumulator;

  public RandomerAI() {
    accumulator = 0;
    genDistance();
  }


  public Direction pathfind(Vector3f current, float delta, float speed, Direction direction) {
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