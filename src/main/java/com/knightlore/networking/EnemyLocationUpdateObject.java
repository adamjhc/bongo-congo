package com.knightlore.networking;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.EnemyState;
import org.joml.Vector3f;

/**
 * Singular enemy location/state data
 *
 * @author Lewis Relph
 */
public class EnemyLocationUpdateObject {

  public Vector3f position;
  public Direction direction;
  public EnemyState state;

  public EnemyLocationUpdateObject(Vector3f position, Direction direction, EnemyState state) {
    this.position = position;
    this.direction = direction;
    this.state = state;
  }
}
