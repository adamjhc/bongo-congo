package com.knightlore.game.entity.ai;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

/**
 * Determines the behaviour of the 'Walker' enemy in-game.
 * @author Jacqui Henes
 */
public class WalkerAI {

  /**
   * Default constructor
   */
  public WalkerAI() {}

  /**
   * Calculates the next position of the enemy. For Walkers,
   * this is just moving forwards.
   *
   * @param current Current enemy position
   * @param delta Time elapsed since last server update
   * @param speed Enemy speed
   * @param direction Current enemy direction
   * @return The next position of the enemy
   */
  public Vector3f pathfind(Vector3f current, float delta, float speed, Direction direction) {
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(speed * delta, newPos);
    return newPos.add(current);
  }
}
