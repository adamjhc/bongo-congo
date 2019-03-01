package com.knightlore.client.render;

import org.joml.Matrix4f;

public class World {

  /** Scale of the world */
  public static final int SCALE = 48;

  /** Projection of the world */
  private Matrix4f projection;

  /** Initialise the render world */
  World() {
    projection = new Matrix4f().scale(SCALE);
  }

  /**
   * Get the projection of the world
   *
   * @return Projection of the world
   */
  Matrix4f getProjection() {
    return projection;
  }
}
