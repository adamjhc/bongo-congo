package com.knightlore.game.entity.ai;

// Idles until player walks into range, then moves towards player
// TODO: Do A* Euclidean distance pathfinding towards player entity

import java.util.List;

import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

public class ChargerAI {
  public float range;


  public Vector3f pathfind(Vector3f position, float delta, int speed, Direction direction) { return new Vector3f();
  }
}
