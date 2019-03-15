package com.knightlore.client.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Camera used in looking around the rendered world
 *
 * @author Adam Cox
 */
public class Camera {

  /** Scaled position in the the camera */
  private Vector3f scaledPosition;

  /** Position in the world of the camera */
  private Vector3f worldPosition;

  /** Projection of the camera */
  private Matrix4f projection;

  /**
   * Initialise the camera
   *
   * @param width Width of the window
   * @param height Height of the window
   * @author Adam Cox
   */
  Camera(int width, int height) {
    scaledPosition = new Vector3f(0, 0, 0);
    projection =
        new Matrix4f()
            .setOrtho2D(
                ((float) -width) / 2,
                ((float) width) / 2,
                ((float) -height) / 2,
                ((float) height) / 2);
  }

  /**
   * Get the world scaledPosition of the camera
   *
   * @return world scaledPosition of the camera
   * @author Adam Cox
   */
  Vector3f getWorldPosition() {
    return worldPosition;
  }

  /**
   * Sets the scaledPosition of the camera in the world
   *
   * @param worldPosition New worldPosition of the camera
   * @author Adam Cox
   */
  public void setPosition(Vector3f worldPosition, int negativeWorldScale) {
    this.scaledPosition = worldPosition.mul(negativeWorldScale, new Vector3f());
    this.worldPosition = worldPosition;
  }

  /**
   * Get the projection of the camera
   *
   * @return Projection of the camera
   * @author Adam Cox
   */
  Matrix4f getProjection() {
    return projection.translate(scaledPosition, new Matrix4f());
  }
}
