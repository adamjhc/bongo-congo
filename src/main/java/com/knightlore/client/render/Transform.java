package com.knightlore.client.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

  /** Position of the transform */
  private Vector3f position;

  /** Scale of the transform */
  private Vector3f scale;

  /**
   * Initialise the transform
   *
   * @param scale Scale of the transform
   */
  public Transform(int scale) {
    position = new Vector3f();
    this.scale = new Vector3f(scale, scale, 1);
  }

  /**
   * Get the position of the transform
   *
   * @return Position of the transform
   */
  public Vector3f getPosition() {
    return position;
  }

  /**
   * Set the position of the transform
   *
   * @param position New position of the transform
   */
  public void setPosition(Vector3f position) {
    this.position = position;
  }

  /**
   * Get the projection of the transform
   *
   * @param target Matrix to manipulate and return
   * @return Projection of the transform
   */
  public Matrix4f getProjection(Matrix4f target) {
    target.scale(scale);
    target.translate(position);

    return target;
  }
}
