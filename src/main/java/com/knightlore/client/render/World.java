package com.knightlore.client.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World {

  /** Scale of the world */
  private int scale;

  /** Projection of the world */
  private Matrix4f projection;

  /** Initialise the render world */
  public World() {
    this.scale = 48;

    projection = new Matrix4f().setTranslation(new Vector3f(0));
    projection.scale(scale);
  }

  /**
   * Get the projection of the world
   *
   * @return Projection of the world
   */
  public Matrix4f getProjection() {
    return projection;
  }

  /**
   * Get the scale of the world
   *
   * @return Scale of the world
   */
  public int getScale() {
    return scale;
  }
}
