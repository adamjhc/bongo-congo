package com.knightlore.game.math;

public class Vector3i extends org.joml.Vector3i {

  public Vector3i(int x, int y, int z) {
    super(x, y, z);
  }

  public boolean isWithinBounds(Vector3i vector) {
    return vector.x >= 0
        && vector.y >= 0
        && vector.z >= 0
        && vector.x <= this.x
        && vector.y <= this.y
        && vector.z <= this.z;
  }
}
