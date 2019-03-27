package com.knightlore.game.entity.ai;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

// Walks back and forth on the same plane
public class WalkerAI {

  public WalkerAI() {

  }

  public Vector3f pathfind(Vector3f current, float delta, float speed, Direction direction) {
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(speed * delta, newPos);
    return newPos.add(current);
  }


}
