package com.knightlore.client.render.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Transform used in moving GameObjects around world
 *
 * @author Adam Cox
 */
public class Transform {

  /** Position of the transform */
  private Vector3f position;

  /**
   * Initialise the transform
   *
   * @author Adam Cox
   */
  Transform() {
    position = new Vector3f();
  }

  /**
   * Get the position of the transform
   *
   * @return Position of the transform
   * @author Adam Cox
   */
  public Vector3f getPosition() {
    return position;
  }

  /**
   * Set the position of the transform
   *
   * @param position New position of the transform
   * @author Adam Cox
   */
  public void setPosition(Vector3f position) {
    this.position = position;
  }

  /**
   * Get the projection of the transform
   *
   * @param target Matrix to manipulate and return
   * @return Projection of the transform
   * @author Adam Cox
   */
  Matrix4f getProjection(Matrix4f target, int worldScale) {
    target.scale(worldScale);
    target.translate(position);

    return target;
  }
}
