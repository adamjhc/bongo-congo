package com.knightlore.game.entity.ai;

// Idles until player walks into range, then moves towards player
// TODO: Do A* Euclidean distance pathfinding towards player entityimport com.knightlore.game.math.Vector3f;
import org.joml.Vector3f;

import java.util.List;

public class ChargerAI extends EnemyAI {
  public float range;

  @Override
    public List<Vector3f> pathfind() {

    return null;
  }
}
