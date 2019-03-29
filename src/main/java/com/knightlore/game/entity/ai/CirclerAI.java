package com.knightlore.game.entity.ai;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

import static com.knightlore.game.entity.Direction.*;

/**
 * Determines behaviour of 'Circler' enemies
 *
 * @author Jacqui Henes
 */
public class CirclerAI {
  private float radius = 70f; // length between enemy position and where they are moving

  /** Default constructor */
  public CirclerAI() {}

  /**
   * Calculates next position for the Circler to move to. This is done by using the spawn point of
   * the enemy with circle geometry to move them around in a circular path.
   *
   * @param home Origin of enemy
   * @param delta Time elapsed since last server update
   * @param angle Current angle from origin to player
   * @return New enemy position
   */
  public Vector3f pathfind(Vector3f home, float delta, int angle) {
    Vector3f newPos = new Vector3f();
    newPos.x = home.x + (float) ((Math.cos(Math.toRadians(angle)) * radius * delta));
    newPos.y = home.y + (float) ((Math.sin(Math.toRadians(angle)) * radius * delta));
    return newPos;
  }

  /**
   * Returns which way the enemy should be facing based on what angle of the circle they are at.
   *
   * @param angle
   * @return Expected enemy direction
   */
  public Direction getDirection(int angle) {
    if (angle >= 0 && angle < 45) {
      return SOUTH_WEST;
    } else if (angle >= 45 && angle < 90) {
      return SOUTH;
    } else if (angle >= 90 && angle < 135) {
      return SOUTH_EAST;
    } else if (angle >= 135 && angle < 180) {
      return EAST;
    } else if (angle >= 180 && angle < 225) {
      return NORTH_EAST;
    } else if (angle >= 225 && angle < 270) {
      return NORTH;
    } else if (angle >= 270 && angle < 315) {
      return NORTH_WEST;
    } else {
      return WEST;
    }
  }
}
