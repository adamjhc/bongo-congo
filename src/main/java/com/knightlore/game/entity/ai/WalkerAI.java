package com.knightlore.game.entity.ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

// Walks back and forth on the same plane
public class WalkerAI extends EnemyAI {

  Direction direction;

  public WalkerAI(Vector3f home, Direction startDirection) {
    this.home = home;
    this.direction = startDirection;
  }

  @Override
  public Vector3f pathfind(Vector3f current) {
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(0.3f, newPos);
    return newPos.add(current);
  }
}
