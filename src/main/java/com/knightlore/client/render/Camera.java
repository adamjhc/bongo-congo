package com.knightlore.client.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

  /** Position in the world of the camera */
  private Vector3f position;

  /** Projection of the camera */
  private Matrix4f projection;

  /**
   * Initialise the camera
   *
   * @param width Width of the window
   * @param height Height of the window
   */
  public Camera(int width, int height) {
    position = new Vector3f(0, 0, 0);
    projection =
        new Matrix4f()
            .setOrtho2D(
                ((float) -width) / 2,
                ((float) width) / 2,
                ((float) -height) / 2,
                ((float) height) / 2);
  }

  /**
   * Sets the position of the camera in the world
   *
   * @param position New position of the camera
   */
  public void setPosition(Vector3f position) {
    this.position = position;
  }

  /**
   * Get the projection of the camera
   *
   * @return Projection of the camera
   */
  public Matrix4f getProjection() {
    return projection.translate(position, new Matrix4f());
  }
}
