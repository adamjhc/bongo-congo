package com.knightlore.client.render;

import com.knightlore.client.io.Window;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * Camera used in looking around the rendered world
 *
 * @author Adam Cox
 */
class Camera {

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
    scaledPosition = new Vector3f();
    worldPosition = new Vector3f();
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
   * @param newWorldPosition New newWorldPosition of the camera
   * @param worldScale
   * @param mapSize
   * @author Adam Cox
   */
  void updatePosition(Vector3f newWorldPosition, int worldScale, Vector3i mapSize) {
    Vector3f newScaledPosition = newWorldPosition.mul(-worldScale, new Vector3f());

    this.scaledPosition.y = newScaledPosition.y;
    this.worldPosition.y = newWorldPosition.y;

    Vector3f leftMapPos = CoordinateUtils.toIsometric(0, mapSize.y - 1f, 0);
    Vector3f rightMapPos = CoordinateUtils.toIsometric(mapSize.x - 1f, 0, 0);

    float cameraMinX = leftMapPos.x * worldScale + Window.ORIGINAL_WIDTH / (float) 2 - worldScale;
    float cameraMaxX = rightMapPos.x * worldScale - Window.ORIGINAL_WIDTH / (float) 2 + worldScale;
    float scaledCameraXPos = newWorldPosition.x * worldScale;

    if (scaledCameraXPos <= cameraMinX && scaledCameraXPos >= cameraMaxX) {
      scaledPosition.x = 0;
      worldPosition.x = 0;
    } else if (scaledCameraXPos <= cameraMinX) {
      if (cameraMaxX <= cameraMinX) {
        scaledPosition.x = 0;
        worldPosition.x = 0;
      } else {
        scaledPosition.x = cameraMinX * -1;
        worldPosition.x = cameraMinX / worldScale;
      }
    } else if (scaledCameraXPos >= cameraMaxX) {
      if (cameraMaxX <= cameraMinX) {
        scaledPosition.x = 0;
        worldPosition.x = 0;
      } else {
        scaledPosition.x = cameraMaxX * -1;
        worldPosition.x = cameraMaxX / worldScale;
      }
    } else {
      scaledPosition.x = newScaledPosition.x;
      worldPosition.x = newWorldPosition.x;
    }
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
