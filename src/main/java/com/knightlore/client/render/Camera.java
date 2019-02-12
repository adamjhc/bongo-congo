package com.knightlore.client.render;

import com.knightlore.game.model.math.Vector3f;
import org.joml.Matrix4f;

public class Camera {

  private Vector3f position;
  private Matrix4f projection;

  public Camera(int width, int height) {
    position = new Vector3f(0, 0, 0);
    projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public void addPosition(Vector3f change) {
    position.add(change);
  }

  public Matrix4f getProjection() {
    return projection;
  }
}
