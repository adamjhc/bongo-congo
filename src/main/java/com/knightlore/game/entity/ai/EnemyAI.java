package com.knightlore.game.entity.ai;

import java.util.LinkedList;
import java.util.List;
import org.joml.Vector3f;

/* Generic Enemy AI
   Requirements:
        - Only walk on floor tiles
        - Only stay on level they were spawned
        - Reverse direction when hits a wall

   For now, default enemy AI doesn't move from their spawn
*/
public abstract class EnemyAI {

  public Vector3f
      home; // Enemy 'start' point and where the pathfinding assumes the enemy is at that moment
  private List<Vector3f> path;

  protected EnemyAI() {}

  EnemyAI(Vector3f home) {
    this.home = home;
  }

  public List<Vector3f> pathfind() {
    this.path = new LinkedList<Vector3f>(); // TODO: confirm what the 'coordinates' are
    return path;
  }

  public void setHome(Vector3f home) {
    this.home = home;
  }
}
