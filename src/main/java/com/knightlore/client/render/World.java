package com.knightlore.client.render;

import org.joml.Matrix4f;

/**
 * World used in OpenGL rendering
 *
 * @author Adam Cox
 */
class World {

  /** Scale of the world */
  private int scale = 48;

  /** Projection of the world */
  private Matrix4f projection;

  /**
   * Initialise the render world
   *
   * @author Adam Cox
   */
  World() {
    projection = new Matrix4f().scale(scale);
  }

  /**
   * Get the scale of the world
   *
   * @return scale of the world
   * @author Adam Cox
   */
  int getScale() {
    return scale;
  }

  /**
   * Sets the scale of the world
   *
   * @param scale New scale of the world
   * @author Adam Cox
   */
  void setScale(int scale) {
    this.scale = scale;
    projection = new Matrix4f().scale(scale);
  }

  /**
   * Get the projection of the world
   *
   * @return Projection of the world
   * @author Adam Cox
   */
  Matrix4f getProjection() {
    return projection;
  }
}
