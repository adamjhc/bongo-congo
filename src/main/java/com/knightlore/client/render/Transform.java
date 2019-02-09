package com.knightlore.client.render;

import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;

public class Transform {

  private Vector3f position;
  private Vector3f scale;

  public Transform(Vector3f scale) {
    position = new Vector3f();
    this.scale = scale;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public Matrix4f getProjection(Matrix4f target) {
    target.scale(scale);
    target.translate(position);

    return target;
  }
}
