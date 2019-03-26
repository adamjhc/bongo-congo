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
    projection = new Matrix4f().setOrtho2D(-width / 2f, width / 2f, -height / 2f, height / 2f);
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
   * Get the projection of the camera
   *
   * @return Projection of the camera
   * @author Adam Cox
   */
  Matrix4f getProjection() {
    return projection.translate(scaledPosition, new Matrix4f());
  }

  /**
   * Set unlimited camera position
   *
   * @param position Position of camera
   * @param worldScale Scale of the world
   * @author Adam Cox
   */
  void setPosition(Vector3f position, int worldScale) {
    scaledPosition = position.mul(-worldScale, new Vector3f());
    worldPosition = new Vector3f(position);
  }

  /**
   * Sets the scaledPosition of the camera in the world
   *
   * @param newWorldPosition New newWorldPosition of the camera
   * @param worldScale Scale of the world
   * @param mapSize Size of the map
   * @author Adam Cox
   */
  void updatePosition(Vector3f newWorldPosition, int worldScale, Vector3i mapSize) {
    Vector3f leftMostMapPos = CoordinateUtils.toIsometric(0, mapSize.y - 1f, 0);
    Vector3f rightMostMapPos = CoordinateUtils.toIsometric(mapSize.x - 1f, 0, 0);
    Vector3f topMostMapPos =
        CoordinateUtils.toIsometric(mapSize.x - 1f, mapSize.y - 1f, mapSize.z - 1f);
    Vector3f bottomMostMapPos = CoordinateUtils.toIsometric(0, 0, 0);

    float cameraMinX = leftMostMapPos.x * worldScale + Window.ORIGINAL_WIDTH / 2f - worldScale;
    float cameraMaxX = rightMostMapPos.x * worldScale - Window.ORIGINAL_WIDTH / 2f + worldScale;
    float cameraMinY = bottomMostMapPos.y * worldScale + Window.ORIGINAL_HEIGHT / 4f - worldScale;
    float cameraMaxY = topMostMapPos.y * worldScale - Window.ORIGINAL_HEIGHT / 4f + worldScale;

    float newX = newWorldPosition.x * worldScale;
    float newY = newWorldPosition.y * worldScale;

    float scaledX = limitCameraAxis(newX, cameraMinX, cameraMaxX);
    float scaledY = limitCameraAxis(newY, cameraMinY, cameraMaxY);

    scaledPosition.x = scaledX * -1;
    scaledPosition.y = scaledY * -1;

    worldPosition.x = scaledX / worldScale;
    worldPosition.y = scaledY / worldScale;
  }

  /**
   * Limits a camera position
   *
   * @param newPos Proposed new position of the camera
   * @param min Minimum position the camera is allowed
   * @param max Maximum position the camera is allowed
   * @return Position within limits
   * @author Adam Cox
   */
  private float limitCameraAxis(float newPos, float min, float max) {
    if (max <= min) return 0;

    if (newPos <= min) {
      return min;
    } else if (newPos >= max) {
      return max;
    } else {
      return newPos;
    }
  }
}
