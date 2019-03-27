package com.knightlore.game.entity.ai;

import java.util.List;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

// Walks in a random direction for a set distance before changing direction and continuing
public class RandomerAI {

  public static int DISTANCE = 100; // number of game updates before changing direction
  public int accumulator;

  public RandomerAI() {
    accumulator = 0;
  }


  public Vector3f pathfind(Vector3f current, float delta, float speed, Direction direction) {
    Vector3f newPos = new Vector3f();

    if (accumulator >= DISTANCE) {
      direction = direction.getRandom();
      direction.getNormalisedDirection().mul(10 * delta, newPos);
      System.out.println(newPos);
      accumulator = 0;
    } else {
      direction.getNormalisedDirection().mul(speed * delta, newPos);
      accumulator++;
    }
    return newPos.add(current);
  }





}