package com.knightlore.client.render.world;

import com.knightlore.client.render.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

  /** Position of the transform */
  private Vector3f position;

  /** Initialise the transform */
  Transform() {
    position = new Vector3f();
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
  Matrix4f getProjection(Matrix4f target) {
    target.scale(World.SCALE);
    target.translate(position);

    return target;
  }
}
